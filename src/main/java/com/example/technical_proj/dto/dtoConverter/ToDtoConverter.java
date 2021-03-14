package com.example.technical_proj.dto.dtoConverter;

import com.example.technical_proj.dto.PostDto;
import com.example.technical_proj.dto.UserDto;
import com.example.technical_proj.model.Post;
import com.example.technical_proj.model.User;

public class ToDtoConverter {

    public static PostDto postToDto(final Post post) {
        return new PostDto(
                post.getId(),
                post.getAuthor(),
                post.getTitle(),
                post.getAttachmentUrl(),
                post.getCreationTime(),
                userToDto(post.getUser())
        );
    }

    public static UserDto userToDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles(),
                user.getAuthorities());
    }
}
