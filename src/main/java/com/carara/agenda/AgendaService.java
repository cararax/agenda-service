package com.carara.agenda;

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

    public Agenda createAgenda(Agenda agenda) {
        if (agenda.getEndDate()==null) {
            agenda.setEndDate(LocalDateTime.now().plusMinutes(1));
        }
        return agendaRepository.save(agenda);
    }

    public Optional<Agenda> findById(Long id) {
        return agendaRepository.findById(id);

    }
}
