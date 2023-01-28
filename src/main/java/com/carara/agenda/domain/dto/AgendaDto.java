package com.carara.agenda.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaDto {
    @NotBlank(message = "Description is mandatory")
    @Size(max = 100, message = "Description must have a maximum of 100 characters")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Future(message = "End date must be in a future time")
    private LocalDateTime endDate;

    public AgendaDto(String description, LocalDateTime endDate) {
        this.description = description;
        this.endDate = endDate;
    }
}
