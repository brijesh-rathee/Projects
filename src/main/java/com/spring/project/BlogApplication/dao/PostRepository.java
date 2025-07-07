package com.spring.project.BlogApplication.dao;

import com.spring.project.BlogApplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
