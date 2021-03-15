package com.example.technical_proj.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private String name;

    @NotNull
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

}