package com.example.technical_proj.service;

import com.example.technical_proj.dto.dtoConverter.ToDtoConverter;
import com.example.technical_proj.dto.PostDto;
import com.example.technical_proj.exceptions.PostException;
import com.example.technical_proj.model.Post;
import com.example.technical_proj.repository.PostRepository;
import com.example.technical_proj.repository.PostSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostSearch postSearch;

    @Autowired
    public PostService(final PostRepository postRepository, final PostSearch postSearch) {
        this.postRepository = postRepository;
        this.postSearch = postSearch;
    }

    @PreAuthorize("hasRole('USER')")
    public PostDto create(final Post post) {
        this.postRepository.save(post);
        return ToDtoConverter.postToDto(post);
    }

    public Collection<PostDto> getAll() {

        Collection<Post> posts = this.postRepository.findAllSortedByDateReverse();
        return posts.stream()
                .map(ToDtoConverter::postToDto)
                .collect(Collectors.toList());
    }

    public PostDto getById(final Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new PostException("Can't get. Post not found!"));
        return ToDtoConverter.postToDto(post);
    }


    @PreAuthorize("hasRole('USER')")
    public PostDto update(final Post post) {
        this.postRepository.findById(post.getId()).orElseThrow(
                () -> new PostException("Can't update. Post not found!")
        );
        this.postRepository.save(post);
        return ToDtoConverter.postToDto(post);
    }

    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        Collection<Post> relatedComments = postRepository.findByPost_Id(id);
        if (relatedComments.size() > 0) {
            for (Post comment : relatedComments) {
                postRepository.deleteById(comment.getId());
            }
        }
        this.postRepository.deleteById(id);
    }

    public void uploadAttachment(final MultipartFile file) throws IOException {
        UUID imgGeneratedId = UUID.nameUUIDFromBytes(file.getBytes());
        File convertFile = new File("src/main/assets/" + imgGeneratedId + file.getOriginalFilename());
        Post foundPost = postRepository.findFirstByOrderByIdDesc();
        foundPost.setAttachmentUrl("./assets/" + imgGeneratedId + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        postRepository.save(foundPost);
    }

    @SuppressWarnings("unchecked")
    public Collection search(final String query) {
        Collection<Post> searchResults;
        try {
            searchResults = postSearch.search(query);
            return searchResults.stream().map(ToDtoConverter::postToDto).collect(Collectors.toList());
        } catch (Exception ignored) {

        }
        return null;
    }
}
