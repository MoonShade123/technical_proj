package com.example.technical_proj.service;

import com.example.technical_proj.dto.dtoConverter.FromDtoConverter;
import com.example.technical_proj.dto.dtoConverter.ToDtoConverter;
import com.example.technical_proj.dto.PostDto;
import com.example.technical_proj.exceptions.PostException;
import com.example.technical_proj.model.Post;
import com.example.technical_proj.model.Role;
import com.example.technical_proj.model.User;
import com.example.technical_proj.repository.PostRepository;
import com.example.technical_proj.repository.PostSearch;
import com.example.technical_proj.repository.RoleRepository;
import com.example.technical_proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostSearch postSearch;

    @Autowired
    public PostService(RoleRepository roleRepository, UserRepository userRepository, PostRepository postRepository, PostSearch postSearch) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postSearch = postSearch;
    }

    @PreAuthorize("hasRole('USER')")
    public PostDto create(final PostDto postDto) {
        Post post = FromDtoConverter.dtoToPost(postDto);
        post.setAuthor(userRepository.getOne(postDto.getAuthorId()));
        List<Role> roles = postDto.getRoleIds().stream().map(roleRepository::getOne).collect(Collectors.toList());
        post.setWithRole(roles);
        post.setCreationTime(LocalDateTime.now());
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
    public PostDto update(final PostDto postDto) {
        Post post = this.postRepository.findById(postDto.getId()).orElseThrow(
                () -> new PostException("Can't update. Post not found!")
        );
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setAttachmentUrl(postDto.getAttachmentUrl());
        this.postRepository.save(post);
        return ToDtoConverter.postToDto(post);
    }

    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        postRepository.deleteById(id);
        this.postRepository.deleteById(id);
    }

    public void uploadAttachment(final MultipartFile file) throws IOException {
        UUID imgGeneratedId = UUID.nameUUIDFromBytes(file.getBytes());
        File convertFile = new File("src/main/assets/" + imgGeneratedId + file.getOriginalFilename());
        Post foundPost = (Post) postRepository.findAll();
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
