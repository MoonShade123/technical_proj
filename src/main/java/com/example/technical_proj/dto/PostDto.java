package com.example.technical_proj.dto;

import com.example.technical_proj.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    private User author;

    private String title;

    private LocalDateTime createTime;

    private UserDto user;
}
