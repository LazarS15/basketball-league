package com.basketball.league.application.dto.create.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreatePlayerCommand {
    @NotNull
    @Size(min = 2, max = 50)
    private final String firstName;
    @NotNull
    @Size(min = 2, max = 50)
    private final String lastName;
    @NotNull
    @Min(0)
    @Max(99)
    private final int jerseyNumber;
    @NotNull
    @Size(min = 3, max = 50)
    private final String teamName;
    @NotNull
    @Size(min = 6, max = 80)
    private final String imagePath;
    @NotNull
    private final String birthDate;

}
