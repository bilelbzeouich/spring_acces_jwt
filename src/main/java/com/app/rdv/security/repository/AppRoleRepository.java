package com.app.rdv.security.repository;

import com.app.rdv.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
    AppRole findByRole(String role);
}
