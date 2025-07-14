package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostService {

    //show all post
    Page<Post> findAll(int page, int size, String sortDirection);

    //create new post
    void save(Post post, String tags);

    //find the post by id
    Post findById(int id);

    //delete post by id
    void deleteById(int id);

    //search by keyword
    Page<Post> searchPosts(String keyword, int pageNumber, int pageSize, String sortDirection);

    // filter posts include searching also so no need of searchPosts
    Page<Post> filterPosts(String keyword, List<String> authors, List<String> tags, int pageNumber, int pageSize, String sortDirection);

    // find all authors from database for dropdown
    List<String> getAllAuthors();

    // find all tags from database for dropdown
    List<String> getAllTags();
}
