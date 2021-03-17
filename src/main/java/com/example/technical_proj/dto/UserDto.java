package com.example.technical_proj.dto;

import com.example.technical_proj.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean isActive;

    private Collection<Role> roles;

}
