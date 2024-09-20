package com.basketball.league.application.dto.create.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class CreateGameCommand {
    @NotNull
    @Min(1)
    private final int round;
    @NotNull
    private final Long homeTeamId;
    @NotNull
    private final Long guestTeamId;
}
