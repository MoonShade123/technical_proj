package com.example.technical_proj.dto.dtoConverter;

import com.example.technical_proj.dto.PostDto;
import com.example.technical_proj.dto.UserDto;
import com.example.technical_proj.model.Post;
import com.example.technical_proj.model.Role;
import com.example.technical_proj.model.User;

import java.util.stream.Collectors;

public class ToDtoConverter {

    public static PostDto postToDto(final Post post) {
        return new PostDto(
                post.getId(),
                post.getAuthor().getId(),
                post.getTitle(),
                post.getText(),
                post.getAttachmentUrl(),
                post.getWithRole().stream().map(Role::getId).collect(Collectors.toList()),
                post.getCreationTime()
        );
    }

    public static UserDto userToDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isActive(),
                user.getRoles());
    }
}
