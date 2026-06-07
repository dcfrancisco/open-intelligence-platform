package com.oip.infrastructure.workspace;

import com.oip.domain.workspace.Workspace;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceJpaRepository extends JpaRepository<Workspace, UUID> {
}
