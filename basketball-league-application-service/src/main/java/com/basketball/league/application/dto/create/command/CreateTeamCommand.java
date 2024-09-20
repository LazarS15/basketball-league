package com.basketball.league.application.dto.create.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateTeamCommand {
    @NotNull
    @Size(min = 2, max = 50)
    private final String name;
    @NotNull
    @Size(min = 15)
    private final String imagePath;
    @NotNull
    @Size(min = 15)
    private final String logoPath;
    @NotNull
    private final String hall;
}
