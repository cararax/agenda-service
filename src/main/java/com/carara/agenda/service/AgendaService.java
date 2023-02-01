package com.carara.agenda.service;

import com.carara.agenda.domain.dto.AgendaDto;
import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.infra.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2(topic = "AgendaService")
public class AgendaService {
    AgendaRepository agendaRepository;

    public Agenda createAgenda(AgendaDto agendaDto) {

        if (agendaDto.getEndDate() == null) {
            agendaDto.setEndDate(LocalDateTime.now().plusMinutes(1));
            log.info("Agenda end date not informed. Setting default value now + 1 min: " + agendaDto.getEndDate());
        }
        Agenda agenda = new Agenda(agendaDto.getDescription(), agendaDto.getEndDate(), false);
        return agendaRepository.save(agenda);
    }

    public Optional<Agenda> findById(Long id) {
        return agendaRepository.findById(id);
    }

    public void updateResultCalculatedToTrue(Long agendaId) {
        log.info("Updating resultCalculated to true for agenda id: " + agendaId);
        agendaRepository.updateResultCalculatedById(true, agendaId);
    }
}
