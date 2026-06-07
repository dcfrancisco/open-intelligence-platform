package com.oip.api.ask;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.oip.api.config.ApiExceptionHandler;
import com.oip.api.config.RequestIdFilter;
import com.oip.domain.ask.AskService;
import com.oip.domain.memory.MemoryService;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AskController.class)
@Import({ApiExceptionHandler.class, RequestIdFilter.class})
class AskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AskService askService;

    @Test
    void returnsGroundedAnswerWithSources() throws Exception {
        UUID workspaceId = UUID.randomUUID();
        UUID sourceId = UUID.randomUUID();
        when(askService.ask(any())).thenReturn(new AskService.AskResult(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Apply Flyway migrations before launching the service.",
                List.of(new AskService.SourceReference(
                        sourceId,
                        "startup-runbook.md",
                        "DOCUMENT",
                        "file:///runbooks/startup-runbook.md")),
                List.of(new MemoryService.MemoryEntryView(
                        UUID.randomUUID(),
                        workspaceId,
                        UUID.randomUUID(),
                        sourceId,
                        "Startup Runbook",
                        "Apply Flyway migrations before launching the service.",
                        List.of("runbook"),
                        "startup-runbook.md",
                        "DOCUMENT",
                        "file:///runbooks/startup-runbook.md",
                        OffsetDateTime.now())),
                new AskService.TimingMetrics(12L, 34L, 46L)));

        mockMvc.perform(post("/api/v1/ask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "workspaceId": "%s",
                                  "question": "How do I start OIP?"
                                }
                                """.formatted(workspaceId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("Apply Flyway migrations before launching the service."))
                .andExpect(jsonPath("$.sources[0].sourceName").value("startup-runbook.md"))
                .andExpect(jsonPath("$.retrievedEntries[0].title").value("Startup Runbook"))
                .andExpect(jsonPath("$.timingMetrics.totalMs").value(46));
    }
}
