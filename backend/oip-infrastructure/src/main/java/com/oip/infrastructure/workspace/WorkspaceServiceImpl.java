package com.oip.infrastructure.workspace;

import com.oip.domain.workspace.Workspace;
import com.oip.domain.workspace.WorkspaceService;
import com.oip.infrastructure.audit.AuditTrailService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceJpaRepository workspaceJpaRepository;
    private final AuditTrailService auditTrailService;

    public WorkspaceServiceImpl(WorkspaceJpaRepository workspaceJpaRepository, AuditTrailService auditTrailService) {
        this.workspaceJpaRepository = workspaceJpaRepository;
        this.auditTrailService = auditTrailService;
    }

    @Override
    public WorkspaceView create(CreateWorkspaceCommand command) {
        Workspace workspace = workspaceJpaRepository.save(new Workspace(command.name(), command.description()));
        auditTrailService.record(workspace.getId(), "workspace.created", "Workspace", workspace.getId(), java.util.Map.of("name", workspace.getName()));
        return toView(workspace);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceView> list() {
        return workspaceJpaRepository.findAll().stream().map(this::toView).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkspaceView> get(UUID workspaceId) {
        return workspaceJpaRepository.findById(workspaceId).map(this::toView);
    }

    private WorkspaceView toView(Workspace workspace) {
        return new WorkspaceView(workspace.getId(), workspace.getName(), workspace.getDescription(), workspace.getCreatedAt());
    }
}
