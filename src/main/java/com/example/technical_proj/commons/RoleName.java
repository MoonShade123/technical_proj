package com.example.technical_proj.commons;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority {
    GUEST, USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    public RoleName[] getName() {
        return RoleName.values();
    }
}
