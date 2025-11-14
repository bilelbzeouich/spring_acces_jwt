package com.app.rdv.repository;

import com.app.rdv.entities.Rdv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RdvRepository extends JpaRepository<Rdv, Integer> {
    Rdv findByPatientIdAndDateRdv(int id, LocalDateTime date);
    Rdv findByMedecinIdAndDateRdv(int id, LocalDateTime date);
}
