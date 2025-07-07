package com.spring.project.BlogApplication.dao;

import com.spring.project.BlogApplication.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
