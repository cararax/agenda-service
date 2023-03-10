package com.carara.agenda.infra.loader;

import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.infra.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2(topic = "DataLoader")
public class DataLoader implements CommandLineRunner {

    AgendaRepository agendaRepository;

    @Override
    public void run(String... args) {
        if (agendaRepository.count() == 0) {
            loadAgendaData();
        }
    }

    public void loadAgendaData() {
        List<Agenda> agendaList = List.of(
                new Agenda("Instalação de portaria eletrônica", LocalDateTime.now().plusMinutes(5), false),
                new Agenda("Aprovação de novo sistema de gerenciamento de lixo", LocalDateTime.now().plusMinutes(10), false),
                new Agenda("Instalação de bicicletário no prédio", LocalDateTime.now().plusMinutes(20), false));
        agendaRepository.saveAll(agendaList);
        log.info("Agenda loaded to database");
    }
}
