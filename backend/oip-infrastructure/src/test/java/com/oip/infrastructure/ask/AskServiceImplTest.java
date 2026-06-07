package com.oip.infrastructure.ask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oip.domain.ask.AskService;
import com.oip.domain.conversation.Conversation;
import com.oip.domain.conversation.Prompt;
import com.oip.domain.conversation.Response;
import com.oip.domain.memory.MemoryService;
import com.oip.domain.router.ProviderRouter;
import com.oip.infrastructure.audit.AuditTrailService;
import com.oip.infrastructure.conversation.ConversationJpaRepository;
import com.oip.infrastructure.conversation.PromptJpaRepository;
import com.oip.infrastructure.conversation.ResponseJpaRepository;
import com.oip.infrastructure.provider.OllamaClient;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class AskServiceImplTest {

    private final com.oip.domain.rag.VectorSearchService vectorSearchService = mock(com.oip.domain.rag.VectorSearchService.class);
    private final com.oip.domain.rag.ContextBuilder contextBuilder = mock(com.oip.domain.rag.ContextBuilder.class);
    private final com.oip.domain.rag.CitationBuilder citationBuilder = mock(com.oip.domain.rag.CitationBuilder.class);
    private final ProviderRouter providerRouter = mock(ProviderRouter.class);
    private final OllamaClient ollamaClient = mock(OllamaClient.class);
    private final ConversationJpaRepository conversationJpaRepository = mock(ConversationJpaRepository.class);
    private final PromptJpaRepository promptJpaRepository = mock(PromptJpaRepository.class);
    private final ResponseJpaRepository responseJpaRepository = mock(ResponseJpaRepository.class);
    private final AuditTrailService auditTrailService = mock(AuditTrailService.class);
    private final AskServiceImpl askService = new AskServiceImpl(
            vectorSearchService,
            contextBuilder,
            citationBuilder,
            providerRouter,
            ollamaClient,
            conversationJpaRepository,
            promptJpaRepository,
            responseJpaRepository,
            auditTrailService,
            new ObjectMapper(),
            5);

    @Test
    void retrievesMemoryBeforeCallingOllamaAndReturnsCitations() {
        UUID workspaceId = UUID.randomUUID();
        MemoryService.MemoryEntryView entry = new MemoryService.MemoryEntryView(
                UUID.randomUUID(),
                workspaceId,
                UUID.randomUUID(),
                UUID.randomUUID(),
                "KT Summary",
                "Run the migration before starting the app.",
                List.of("runbook", "database"),
                "runbook.md",
                "DOCUMENT",
                "file:///knowledge/runbook.md",
                java.time.OffsetDateTime.now());
        AskService.SourceReference sourceReference = new AskService.SourceReference(
                entry.sourceId(),
                entry.sourceName(),
                entry.sourceType(),
                entry.sourceUri());

        when(vectorSearchService.search(workspaceId, null, "How do I start the platform?", 5))
                .thenReturn(List.of(entry));
        when(citationBuilder.build(List.of(entry))).thenReturn(List.of(sourceReference));
        when(contextBuilder.build("How do I start the platform?", List.of(entry)))
                .thenReturn("Context: Run the migration before starting the app.");
        when(providerRouter.route(any(ProviderRouter.AskRoutingRequest.class)))
                .thenReturn(new ProviderRouter.ProviderSelection(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Ollama Local",
                        "http://ollama:11434",
                        "llama3.2:1b"));
        when(ollamaClient.chat("http://ollama:11434", "llama3.2:1b", "Context: Run the migration before starting the app."))
                .thenReturn("Start PostgreSQL, apply Flyway, then launch the application.");
        when(conversationJpaRepository.save(any(Conversation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(promptJpaRepository.save(any(Prompt.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(responseJpaRepository.save(any(Response.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AskService.AskResult result = askService.ask(new AskService.AskCommand(
                workspaceId,
                null,
                null,
                "How do I start the platform?"));

        InOrder inOrder = inOrder(vectorSearchService, contextBuilder, providerRouter, ollamaClient);
        inOrder.verify(vectorSearchService).search(workspaceId, null, "How do I start the platform?", 5);
        inOrder.verify(contextBuilder).build("How do I start the platform?", List.of(entry));
        inOrder.verify(providerRouter).route(any(ProviderRouter.AskRoutingRequest.class));
        inOrder.verify(ollamaClient).chat("http://ollama:11434", "llama3.2:1b", "Context: Run the migration before starting the app.");

        assertThat(result.answer()).contains("Flyway");
        assertThat(result.sources()).containsExactly(sourceReference);
        assertThat(result.retrievedEntries()).containsExactly(entry);
        assertThat(result.timingMetrics().totalMs()).isGreaterThanOrEqualTo(0L);
    }
}
