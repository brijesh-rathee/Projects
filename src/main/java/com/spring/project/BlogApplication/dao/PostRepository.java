package com.spring.project.BlogApplication.dao;

import com.spring.project.BlogApplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN p.tags t "
            + "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(p.excerpt) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);
}
