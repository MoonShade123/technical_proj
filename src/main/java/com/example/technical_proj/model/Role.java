package com.example.technical_proj.model;

import javax.persistence.*;

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
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RoleName name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


}