package com.carara.agenda.controller;

import com.carara.agenda.AgendaService;
import com.carara.agenda.domain.entity.Agenda;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        return agendaService.findById(id).<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("Agenda not found for id {%s}.", id)));
    }

    @PostMapping
    public ResponseEntity<Agenda> createAgenda(@Valid @RequestBody Agenda agenda) {
        Agenda agendaEntity = agendaService.createAgenda(agenda);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agendaEntity.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(agendaEntity);
    }
}
