package com.oip.domain.workspace;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceService {

    WorkspaceView create(CreateWorkspaceCommand command);

    List<WorkspaceView> list();

    Optional<WorkspaceView> get(UUID workspaceId);

    record CreateWorkspaceCommand(String name, String description) {
    }

    record WorkspaceView(UUID id, String name, String description, OffsetDateTime createdAt) {
    }
}
