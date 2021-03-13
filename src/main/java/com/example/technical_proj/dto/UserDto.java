package com.example.technical_proj.dto;

import com.example.technical_proj.commons.RoleName;
import com.example.technical_proj.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String email;

    private Boolean isActive;

    private Set<RoleName> roles;

    public UserDto(Long id, String username, String email, Set<RoleName> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    }

    public UserDto(Long id, String username, String email, Set<RoleName> roles, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.isActive = getIsActive();
    }
}
