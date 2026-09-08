package com.webApp.blog.repository;

import com.webApp.blog.model.Comment;
import com.webApp.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByPostId(Long postId);
    boolean existsByIdAndUser_Id(Long id, Long userId);

}
