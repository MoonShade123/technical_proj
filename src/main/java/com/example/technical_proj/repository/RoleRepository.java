package com.example.technical_proj.repository;

import com.example.technical_proj.commons.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleName, Long> {
    RoleName findRoleByName(final String name);
}

