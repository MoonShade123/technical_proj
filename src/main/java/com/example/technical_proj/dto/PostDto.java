package com.example.technical_proj.dto;

import com.example.technical_proj.model.Role;
import com.example.technical_proj.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    private Long authorId;

    private String text;

    private String title;

    private String attachmentUrl;

    private List<Long> roleIds;

    private LocalDateTime createTime;

}
