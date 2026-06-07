package com.oip.api.memory;

import com.oip.domain.memory.MemoryService;
import com.oip.domain.memory.MemorySourceType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Memory API")
public class MemoryController {

    private final MemoryService memoryService;

    public MemoryController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    @PostMapping("/workspaces/{workspaceId}/memory/collections")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create collection")
    public MemoryService.MemoryCollectionView createCollection(
            @PathVariable UUID workspaceId,
            @Valid @RequestBody CreateCollectionRequest request) {
        return memoryService.createCollection(new MemoryService.CreateMemoryCollectionCommand(
                workspaceId,
                request.name(),
                request.description()));
    }

    @GetMapping("/workspaces/{workspaceId}/memory/collections")
    @Operation(summary = "List collections")
    public List<MemoryService.MemoryCollectionView> listCollections(@PathVariable UUID workspaceId) {
        return memoryService.listCollections(workspaceId);
    }

    @PostMapping("/memory/sources")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload document metadata")
    public MemoryService.MemorySourceView uploadDocumentMetadata(@Valid @RequestBody UploadDocumentMetadataRequest request) {
        return memoryService.uploadDocumentMetadata(new MemoryService.UploadDocumentMetadataCommand(
                request.workspaceId(),
                request.collectionId(),
                request.sourceType(),
                request.name(),
                request.uri(),
                request.contentType()));
    }

    @PostMapping("/memory/entries")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create memory entry")
    public MemoryService.MemoryEntryView createMemoryEntry(@Valid @RequestBody CreateMemoryEntryRequest request) {
        return memoryService.createMemoryEntry(new MemoryService.CreateMemoryEntryCommand(
                request.workspaceId(),
                request.collectionId(),
                request.sourceId(),
                request.title(),
                request.content(),
                request.tags()));
    }

    @GetMapping("/memory/search")
    @Operation(summary = "Search memory")
    public List<MemoryService.MemoryEntryView> search(
            @RequestParam UUID workspaceId,
            @RequestParam(required = false) UUID collectionId,
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit) {
        return memoryService.searchMemory(new MemoryService.SearchMemoryQuery(workspaceId, collectionId, query, limit));
    }

    @GetMapping("/memory/entries/{entryId}")
    @Operation(summary = "Get memory entry")
    public MemoryService.MemoryEntryView getMemoryEntry(@PathVariable UUID entryId) {
        return memoryService.getMemoryEntry(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Memory entry not found"));
    }

    @DeleteMapping("/memory/entries/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete memory entry")
    public void deleteMemoryEntry(@PathVariable UUID entryId) {
        memoryService.deleteMemoryEntry(entryId);
    }

    public record CreateCollectionRequest(@NotBlank String name, String description) {
    }

    public record UploadDocumentMetadataRequest(
            @NotNull UUID workspaceId,
            @NotNull UUID collectionId,
            @NotNull MemorySourceType sourceType,
            @NotBlank String name,
            String uri,
            String contentType) {
    }

    public record CreateMemoryEntryRequest(
            @NotNull UUID workspaceId,
            @NotNull UUID collectionId,
            UUID sourceId,
            @NotBlank String title,
            @NotBlank String content,
            List<String> tags) {
    }
}
