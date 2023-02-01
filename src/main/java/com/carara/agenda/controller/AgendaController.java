package com.carara.agenda.controller;

import com.carara.agenda.domain.dto.AgendaDto;
import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.infra.message.ResultListener;
import com.carara.agenda.infra.message.response.Result;
import com.carara.agenda.service.AgendaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/agendas")
@AllArgsConstructor
@Log4j2(topic = "AgendaController")
public class AgendaController {

    AgendaService agendaService;
    ResultListener resultListener;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        log.info("Find agenda by id: " + id);
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
        log.info("Agenda created: " + agendaEntity.toString());
        return ResponseEntity.created(location)
                .body(agendaDto);
    }
}
