package com.basketball.league.application.dto.create.response;

import com.basketball.league.domain.entity.Team;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class CreateTeamResponse {
    @NotNull
    private final Team team;
    @NotNull
    private final String message;
}
