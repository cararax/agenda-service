package com.carara.agenda.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendaDto {
    private final String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    public AgendaDto(String description, LocalDateTime endDate) {
        this.description = description;
        this.endDate = endDate;
    }
}
