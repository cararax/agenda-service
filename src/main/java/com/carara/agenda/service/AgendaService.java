package com.carara.agenda.service;

import com.carara.agenda.domain.dto.AgendaDto;
import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.infra.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendaService {
    AgendaRepository agendaRepository;

    public Agenda createAgenda(AgendaDto agendaDto) {

        if (agendaDto.getEndDate() == null) {
            agendaDto.setEndDate(LocalDateTime.now().plusMinutes(1));
        }
        Agenda agenda = new Agenda(agendaDto.getDescription(), agendaDto.getEndDate(), false);
        return agendaRepository.save(agenda);
    }

    public Optional<Agenda> findById(Long id) {
        return agendaRepository.findById(id);
    }

    public void updateResultCalculatedToTrue(Long agendaId) {
        agendaRepository.updateResultCalculatedById(true, agendaId);
    }
}
