package com.example.technical_proj.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.example.technical_proj.commons.RoleName;
import lombok.*;

import java.util.Collection;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @NotNull
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

}