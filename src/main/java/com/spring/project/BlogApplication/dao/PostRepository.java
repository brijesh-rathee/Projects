package com.spring.project.BlogApplication.dao;

import com.spring.project.BlogApplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN p.tags t "
            + "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT p.author from Post p")
    List<String> findAllAuthors();

    @Query("SELECT p FROM Post p WHERE p.author IN :authors")
    Page<Post> findByAuthors(@Param("authors") List<String> authors, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE t.name IN :tags")
    Page<Post> findByTags(@Param("tags") List<String> tags, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE p.author IN :authors AND t.name IN :tags")
    Page<Post> findByAuthorsAndTags(@Param("tags") List<String> tags,
                                    @Param("authors") List<String> authors, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND p.author IN :authors")
    Page<Post> findByKeywordAndAuthors(@Param("keyword") String keyword,
                                      @Param("authors") List<String> authors, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND t.name IN :tags")
    Page<Post> findByKeywordAndTags(@Param("keyword") String keyword,
                                   @Param("tags") List<String> tags, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND p.author IN :authors AND t.name IN :tags")
    Page<Post> findByKeywordAuthorsTags(@Param("keyword") String keyword,
                                       @Param("authors") List<String> authors,
                                       @Param("tags") List<String> tags, Pageable pageable);

}
