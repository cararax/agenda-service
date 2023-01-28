package com.carara.agenda.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "agenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 100, message = "Description must have a maximum of 100 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Future(message = "End date must be in a future time")
    @Column(name = "endDate", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "resultCalculated", nullable = false)
    private boolean resultCalculated;

    public Agenda(String description, LocalDateTime endDate, boolean resultCalculated) {
        this.description = description;
        this.endDate = endDate;
        this.resultCalculated = resultCalculated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Agenda agenda = (Agenda) o;
        return id != null && Objects.equals(id, agenda.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
