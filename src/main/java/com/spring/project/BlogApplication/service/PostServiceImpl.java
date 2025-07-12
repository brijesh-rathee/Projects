package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.dao.PostRepository;
import com.spring.project.BlogApplication.entity.Post;
import com.spring.project.BlogApplication.entity.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final TagService tagService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    @Override
    public Page<Post> findAll(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public void save(Post post, String tags) {
        Set<Tag> tagSet = new HashSet<>();
        String[] tagArray = tags.split(",");

        for(String name : tagArray) {
            String tagName = name.trim();

            if(!tagName.isEmpty()) {
                Optional<Tag> existingTag = tagService.findByName(tagName);

                Tag tag = existingTag.orElseGet(() -> tagService.createTag(tagName));
                tagSet.add(tag);
            }
        }

        if(post.getId() != 0) {
            // it's a update
            Post existingPost = postRepository.findById(post.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found with id : " + post.getId()));

            existingPost.setTitle(post.getTitle());
            existingPost.setExcerpt(post.getExcerpt());
            existingPost.setContent(post.getContent());
            existingPost.setTags(tagSet);
            existingPost.setUpdatedAt(LocalDateTime.now());

            postRepository.save(existingPost);
        } else {
            // it's a new post
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setPublished(true);
            post.setPublishedAt(LocalDateTime.now());
            post.setTags(tagSet);

            postRepository.save(post);
        }
    }

    @Override
    public Post findById(int id) {
        Optional<Post> post = postRepository.findById(id);
        Post thePost = null;
        if(post.isPresent()) {
            thePost = post.get();
        } else {
            throw new RuntimeException("Did not found post by id - " + id);
        }
        return thePost;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> searchPosts(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return postRepository.searchPosts(keyword,pageable);
    }
}
