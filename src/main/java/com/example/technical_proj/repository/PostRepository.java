package com.example.technical_proj.repository;

import com.example.technical_proj.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteById(final Long postId);

    @Query("from Post p order by p.creationTime desc")
    Collection<Post> findAllSortedByDateReverse();


    @Query("from Post p order by p.creationTime desc")
    Page<Post> findAll(final Pageable pageable);

}
