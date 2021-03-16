package com.example.technical_proj.model;

import lombok.*;
import org.hibernate.search.annotations.Field;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Indexed
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @Column
    private String text;

    @Column
    private String title;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> withRole;

    private String attachmentUrl;

}
