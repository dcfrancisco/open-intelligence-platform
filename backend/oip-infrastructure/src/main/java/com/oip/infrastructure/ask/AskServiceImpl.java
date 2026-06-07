package com.oip.infrastructure.ask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oip.domain.ask.AskService;
import com.oip.domain.conversation.Conversation;
import com.oip.domain.conversation.Prompt;
import com.oip.domain.conversation.Response;
import com.oip.domain.memory.MemoryService;
import com.oip.domain.rag.CitationBuilder;
import com.oip.domain.rag.ContextBuilder;
import com.oip.domain.rag.VectorSearchService;
import com.oip.domain.router.ProviderRouter;
import com.oip.infrastructure.audit.AuditTrailService;
import com.oip.infrastructure.conversation.ConversationJpaRepository;
import com.oip.infrastructure.conversation.PromptJpaRepository;
import com.oip.infrastructure.conversation.ResponseJpaRepository;
import com.oip.infrastructure.provider.OllamaClient;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AskServiceImpl implements AskService {

    private final VectorSearchService vectorSearchService;
    private final ContextBuilder contextBuilder;
    private final CitationBuilder citationBuilder;
    private final ProviderRouter providerRouter;
    private final OllamaClient ollamaClient;
    private final ConversationJpaRepository conversationJpaRepository;
    private final PromptJpaRepository promptJpaRepository;
    private final ResponseJpaRepository responseJpaRepository;
    private final AuditTrailService auditTrailService;
    private final ObjectMapper objectMapper;
    private final int searchLimit;

    public AskServiceImpl(
            VectorSearchService vectorSearchService,
            ContextBuilder contextBuilder,
            CitationBuilder citationBuilder,
            ProviderRouter providerRouter,
            OllamaClient ollamaClient,
            ConversationJpaRepository conversationJpaRepository,
            PromptJpaRepository promptJpaRepository,
            ResponseJpaRepository responseJpaRepository,
            AuditTrailService auditTrailService,
            ObjectMapper objectMapper,
            @Value("${oip.rag.search-limit}") int searchLimit) {
        this.vectorSearchService = vectorSearchService;
        this.contextBuilder = contextBuilder;
        this.citationBuilder = citationBuilder;
        this.providerRouter = providerRouter;
        this.ollamaClient = ollamaClient;
        this.conversationJpaRepository = conversationJpaRepository;
        this.promptJpaRepository = promptJpaRepository;
        this.responseJpaRepository = responseJpaRepository;
        this.auditTrailService = auditTrailService;
        this.objectMapper = objectMapper;
        this.searchLimit = searchLimit;
    }

    @Override
    public AskResult ask(AskCommand command) {
        long startedAt = System.currentTimeMillis();

        long retrievalStarted = System.currentTimeMillis();
        List<MemoryService.MemoryEntryView> entries = vectorSearchService.search(
                command.workspaceId(),
                command.collectionId(),
                command.question(),
                searchLimit);
        List<SourceReference> sources = citationBuilder.build(entries);
        String context = contextBuilder.build(command.question(), entries);
        long retrievalMs = System.currentTimeMillis() - retrievalStarted;

        ProviderRouter.ProviderSelection selection = providerRouter.route(
                new ProviderRouter.AskRoutingRequest(command.workspaceId(), command.question()));

        long generationStarted = System.currentTimeMillis();
        String answer = ollamaClient.chat(selection.baseUrl(), selection.modelName(), context);
        long generationMs = System.currentTimeMillis() - generationStarted;
        long totalMs = System.currentTimeMillis() - startedAt;

        Conversation conversation = command.conversationId() == null
                ? conversationJpaRepository.save(new Conversation(command.workspaceId()))
                : conversationJpaRepository.findById(command.conversationId())
                        .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));
        Prompt prompt = promptJpaRepository.save(new Prompt(conversation.getId(), command.question()));
        Response response = responseJpaRepository.save(new Response(
                prompt.getId(),
                selection.providerName(),
                selection.modelName(),
                answer,
                serializeSources(sources),
                retrievalMs,
                generationMs,
                totalMs));

        auditTrailService.record(command.workspaceId(), "ask.executed", "Response", response.getId(), Map.of(
                "provider", selection.providerName(),
                "model", selection.modelName(),
                "retrievedEntries", entries.size()));

        return new AskResult(
                conversation.getId(),
                prompt.getId(),
                response.getId(),
                answer,
                sources,
                entries,
                new TimingMetrics(retrievalMs, generationMs, totalMs));
    }

    private String serializeSources(List<SourceReference> sources) {
        try {
            return objectMapper.writeValueAsString(sources);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize source references", ex);
        }
    }
}
