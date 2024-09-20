package com.basketball.league.application.dto.create.response;

import com.basketball.league.domain.entity.Player;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class CreatePlayerResponse {
    @NotNull
    private final Player player;
    @NotNull
    private final String message;
}
