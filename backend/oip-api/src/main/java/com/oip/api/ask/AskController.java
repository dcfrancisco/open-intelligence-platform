package com.oip.api.ask;

import com.oip.domain.ask.AskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ask")
@Tag(name = "Ask API")
public class AskController {

    private final AskService askService;

    public AskController(AskService askService) {
        this.askService = askService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Ask grounded question")
    public AskService.AskResult ask(@Valid @RequestBody AskRequest request) {
        return askService.ask(new AskService.AskCommand(
                request.workspaceId(),
                request.collectionId(),
                request.conversationId(),
                request.question()));
    }

    public record AskRequest(
            @NotNull UUID workspaceId,
            UUID collectionId,
            UUID conversationId,
            @NotBlank String question) {
    }
}
