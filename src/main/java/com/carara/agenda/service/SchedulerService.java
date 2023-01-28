package com.carara.agenda.service;

import com.carara.agenda.domain.projection.AgendaId;
import com.carara.agenda.infra.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SchedulerService {

    AgendaRepository agendaRepository;

    @Scheduled(cron = "1 * * * * *")
    public void scheduledMethod() {
        List<AgendaId> pastAgendaIdsWithoutResult = agendaRepository
                .findByResultCalculatedFalseAndEndDateBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        //todo: send pastAgendaIdsWithoutResult to a queue for processing result
        //todo: update agendas.resultCalculated to true after processing result
        System.out.println("pastAgendaIdsWithoutResult = " + pastAgendaIdsWithoutResult);
    }
}
