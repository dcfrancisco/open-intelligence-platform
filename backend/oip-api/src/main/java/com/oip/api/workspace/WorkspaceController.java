package com.oip.api.workspace;

import com.oip.domain.workspace.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workspaces")
@Tag(name = "Workspace API")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create workspace")
    public WorkspaceService.WorkspaceView create(@Valid @RequestBody CreateWorkspaceRequest request) {
        return workspaceService.create(new WorkspaceService.CreateWorkspaceCommand(request.name(), request.description()));
    }

    @GetMapping
    @Operation(summary = "List workspaces")
    public List<WorkspaceService.WorkspaceView> list() {
        return workspaceService.list();
    }

    public record CreateWorkspaceRequest(@NotBlank String name, String description) {
    }
}
