package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.entity.Comment;

public interface CommentService {
    void save(Comment comment);

    Comment findById(int id);

    void deleteById(int id);
}
