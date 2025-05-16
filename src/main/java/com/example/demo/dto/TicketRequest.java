package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequest {
    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    @NotBlank(message = "Passenger name is required")
    private String passengerName;
}