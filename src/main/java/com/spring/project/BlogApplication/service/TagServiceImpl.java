package com.spring.project.BlogApplication.service;

import com.spring.project.BlogApplication.dao.TagRepository;
import com.spring.project.BlogApplication.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag createTag(String name) {
        Tag tag = new Tag();

        tag.setName(name);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());

        return tagRepository.save(tag);
    }
}
