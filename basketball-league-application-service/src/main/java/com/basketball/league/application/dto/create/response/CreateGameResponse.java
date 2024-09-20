package com.basketball.league.application.dto.create.response;

import com.basketball.league.domain.entity.Game;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateGameResponse {
    @NotNull
    private final Game game;
    @NotNull
    private final String message;
}
