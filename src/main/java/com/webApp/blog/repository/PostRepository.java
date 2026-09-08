package com.webApp.blog.repository;

import com.webApp.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    @EntityGraph(attributePaths = "author")
    List<Post> findAll();

    @Override
    @EntityGraph(attributePaths = "author")
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "author")
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    boolean existsByIdAndAuthor_Id(Long id, Long authorId);

    @EntityGraph(attributePaths = "author")
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String titleKeyword,
            String contentKeyword,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"author", "comments", "comments.user"})
    @Query("select distinct p from Post p where p.id = :id")
    Optional<Post> findWithDetailsById(@Param("id") Long id);

    @Query(value = """
            select count(*)
            from post_bookmarks
            where post_id = :postId and user_id = :userId
            """, nativeQuery = true)
    long countBookmark(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query(value = "select count(*) from post_bookmarks where post_id = :postId", nativeQuery = true)
    long countBookmarksByPostId(@Param("postId") Long postId);

    @Modifying
    @Query(value = "insert into post_bookmarks (post_id, user_id) values (:postId, :userId)", nativeQuery = true)
    void addBookmark(@Param("postId") Long postId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from post_bookmarks where post_id = :postId and user_id = :userId", nativeQuery = true)
    void removeBookmark(@Param("postId") Long postId, @Param("userId") Long userId);
}
