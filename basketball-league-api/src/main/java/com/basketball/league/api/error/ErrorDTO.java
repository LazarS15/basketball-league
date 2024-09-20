package com.basketball.league.api.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {
    private LocalDateTime timeStamp;
    private int code;
    private String message;
}
