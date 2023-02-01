package com.carara.agenda.service;

import com.carara.agenda.domain.projection.AgendaId;
import com.carara.agenda.infra.message.ResultListener;
import com.carara.agenda.infra.message.response.Result;
import com.carara.agenda.infra.repository.AgendaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SchedulerService {

    AgendaRepository agendaRepository;
    ResultListener resultListener;

    @Scheduled(cron = "1 * * * * *")
    public void scheduledMethod() {
        List<AgendaId> pastAgendaIdsWithoutResult = agendaRepository
                .findByResultCalculatedFalseAndEndDateBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        System.out.println("pastAgendaIdsWithoutResult = " + pastAgendaIdsWithoutResult);
        pastAgendaIdsWithoutResult.forEach(agendaId -> {
            try {
                Result result = resultListener.listen(agendaId.getId());
                if (result != null) {
                    agendaRepository.updateResultCalculatedById(true, agendaId.getId());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing result");
            }
        });
    }
}
