package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.entity.Post;

import java.util.List;

public interface PostService {

    //show all post
    List<Post> findAll();

    //create new post
    void save(Post post, String tags);

    //find the post by id
    Post findById(int id);

    void deleteById(int id);
}
