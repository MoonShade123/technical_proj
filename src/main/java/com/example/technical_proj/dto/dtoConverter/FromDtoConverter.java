package com.example.technical_proj.dto.dtoConverter;

import com.example.technical_proj.dto.PostDto;
import com.example.technical_proj.model.Post;

public class FromDtoConverter {
    public static Post dtoToPost(final PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setAttachmentUrl(postDto.getAttachmentUrl());
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());

        return post;
    }
}
