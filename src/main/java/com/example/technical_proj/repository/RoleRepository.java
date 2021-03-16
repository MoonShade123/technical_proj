package com.example.technical_proj.repository;

import com.example.technical_proj.commons.RoleName;
import com.example.technical_proj.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
}

