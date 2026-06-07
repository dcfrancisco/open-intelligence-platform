package com.oip.api.workspace;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.oip.api.config.ApiExceptionHandler;
import com.oip.api.config.RequestIdFilter;
import com.oip.domain.workspace.WorkspaceService;
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

@WebMvcTest(controllers = WorkspaceController.class)
@Import({ApiExceptionHandler.class, RequestIdFilter.class})
class WorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkspaceService workspaceService;

    @Test
    void createsWorkspace() throws Exception {
        WorkspaceService.WorkspaceView workspaceView = new WorkspaceService.WorkspaceView(
                UUID.randomUUID(),
                "Engineering",
                "Shared delivery knowledge",
                OffsetDateTime.now());
        when(workspaceService.create(any())).thenReturn(workspaceView);

        mockMvc.perform(post("/api/v1/workspaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Engineering",
                                  "description": "Shared delivery knowledge"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("X-Request-Id"))
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.description").value("Shared delivery knowledge"));
    }

    @Test
    void listsWorkspaces() throws Exception {
        when(workspaceService.list()).thenReturn(List.of(new WorkspaceService.WorkspaceView(
                UUID.randomUUID(),
                "Project Memory",
                "Primary knowledge workspace",
                OffsetDateTime.now())));

        mockMvc.perform(get("/api/v1/workspaces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Project Memory"));
    }
}
