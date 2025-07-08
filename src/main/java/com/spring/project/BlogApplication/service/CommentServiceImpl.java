package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.dao.CommentRepository;
import com.spring.project.BlogApplication.entity.Comment;
import com.spring.project.BlogApplication.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    @Override
    @Transactional
    public void save(Comment comment) {
        Post post = postService.findById(comment.getPost().getId());

        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public Comment findById(int id) {
        Optional<Comment> comment = commentRepository.findById(id);
        Comment theComment = null;

        if(comment.isPresent()) {
            theComment = comment.get();
        } else {
            throw new RuntimeException("Did not find comment id - " + id);
        }

        return theComment;
    }

    @Override
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }
}
