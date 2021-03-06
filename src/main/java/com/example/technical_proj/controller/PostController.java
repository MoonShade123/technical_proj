package com.example.technical_proj.controller;

import com.example.technical_proj.dto.PostDto;
import com.example.technical_proj.model.Post;
import com.example.technical_proj.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody final PostDto postDto) {
        return new ResponseEntity<>(this.postService.create(postDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<PostDto>> getAllPosts() {
        return new ResponseEntity<>(this.postService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.postService.getById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestBody final PostDto postDto) {
        return new ResponseEntity<>(this.postService.update(postDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById(@PathVariable final Long id) {
        this.postService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImageOnServer(@RequestParam("file") MultipartFile file) throws IOException {
        this.postService.uploadAttachment(file);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Collection<PostDto>> searchPost(String q) {
        return new ResponseEntity<>(this.postService.search(q), HttpStatus.OK);
    }
}
