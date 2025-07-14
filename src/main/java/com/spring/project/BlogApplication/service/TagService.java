package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    // find tag by name
    Optional<Tag> findByName(String name);

    //create tag
    Tag createTag(String name);

    //get all tags
    List<String> getAllTags();
}
