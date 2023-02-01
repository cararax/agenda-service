package com.carara.agenda.infra.repository;

import com.carara.agenda.domain.entity.Agenda;
import com.carara.agenda.domain.projection.AgendaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<AgendaId> findByResultCalculatedFalseAndEndDateBetween(LocalDateTime endDateStart, LocalDateTime endDateEnd);

    @Transactional
    @Modifying
    @Query("update Agenda a set a.resultCalculated = ?1 where a.id = ?2")
    void updateResultCalculatedById(boolean resultCalculated, Long id);

}