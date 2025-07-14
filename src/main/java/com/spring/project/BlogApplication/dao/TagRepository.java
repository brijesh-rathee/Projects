package com.spring.project.BlogApplication.dao;

import com.spring.project.BlogApplication.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);

    @Query("SELECT DISTINCT t.name FROM Tag t")
    List<String> findAllTagNames();
}
