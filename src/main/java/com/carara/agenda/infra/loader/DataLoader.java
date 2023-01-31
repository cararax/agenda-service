package com.carara.agenda.infra.loader;

import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.infra.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    AgendaRepository agendaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (agendaRepository.count() == 0) {
            loadAssociateData();
        }

    }

    public void loadAssociateData() {
        List<Agenda> agendaList = List.of(
                new Agenda("Instalação de portaria eletrônica", LocalDateTime.now().plusMinutes(5), false),
                new Agenda("Instalação de painéis solares para gerar energia", LocalDateTime.now().plusMinutes(5), false),
                new Agenda("Aprovação de novo sistema de gerenciamento de lixo", LocalDateTime.now().plusMinutes(10), false),
                new Agenda("Mudanças na decoração do hall de entrada", LocalDateTime.now().plusMinutes(15), false),
                new Agenda("Instalação de nova sala de jogos", LocalDateTime.now().plusMinutes(15), false),
                new Agenda("Instalação de bicicletário no prédio", LocalDateTime.now().plusMinutes(20), false));
        agendaRepository.saveAll(agendaList);
    }
}
