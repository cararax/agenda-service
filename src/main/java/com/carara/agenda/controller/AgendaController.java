package com.carara.agenda.controller;

import com.carara.agenda.domain.dto.AgendaDto;
import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.infra.message.ResultListener;
import com.carara.agenda.infra.message.response.Result;
import com.carara.agenda.service.AgendaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/agendas")
@AllArgsConstructor
public class AgendaController {

    AgendaService agendaService;
    ResultListener resultListener;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        return agendaService.findById(id)
                .<ResponseEntity<Object>>map(agenda -> {
                    AgendaDto dto = new AgendaDto(agenda.getDescription(), agenda.getEndDate());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("Agenda not found for id {%s}.", id)));
    }

    @PostMapping
    public ResponseEntity<AgendaDto> createAgenda(@Valid @RequestBody AgendaDto agendaDto) {
        Agenda agendaEntity = agendaService.createAgenda(agendaDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agendaEntity.getId())
                .toUri();
        BeanUtils.copyProperties(agendaEntity, agendaDto);
        return ResponseEntity.created(location)
                .body(agendaDto);
    }
}
