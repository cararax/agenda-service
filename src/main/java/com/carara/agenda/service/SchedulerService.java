package com.carara.agenda.service;

import com.carara.agenda.domain.projection.AgendaId;
import com.carara.agenda.infra.message.ResultListener;
import com.carara.agenda.infra.message.response.Result;
import com.carara.agenda.infra.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2(topic = "SchedulerService")
public class SchedulerService {

    AgendaRepository agendaRepository;
    AgendaService agendaService;
    ResultListener resultListener;

    @Scheduled(cron = "1 * * * * *")
    public void scheduledMethod() {
        log.info("Starting scheduled method at " + LocalDateTime.now() + "");
        List<AgendaId> pastAgendaIdsWithoutResult = agendaRepository
                .findByResultCalculatedFalseAndEndDateBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        if (pastAgendaIdsWithoutResult.isEmpty()) {
            log.info("No agenda found to be processed");
            return;
        } else {
            log.info("Found " + pastAgendaIdsWithoutResult.size() + " agenda(s) to be processed, agenda ids: "
                    + pastAgendaIdsWithoutResult.toString());
        }
        pastAgendaIdsWithoutResult.forEach(agendaId -> {
            try {
                Result result = resultListener.listen(agendaId.getId());
                if (result != null) {
                    agendaService.updateResultCalculatedToTrue(agendaId.getId());
                }
            } catch (Exception e) {
                log.error("Error while trying to get result for agenda id: " + agendaId.getId(), e.getCause());
            }
        });
        log.info("Finished scheduled method at " + LocalDateTime.now() + "");
    }
}
