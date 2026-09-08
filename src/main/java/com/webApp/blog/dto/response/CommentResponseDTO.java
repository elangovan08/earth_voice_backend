package com.webApp.blog.dto.response;

import com.webApp.blog.model.Comment;

import java.time.LocalDateTime;

public class CommentResponseDTO {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;
    private UserResponseDTO user;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String content, LocalDateTime createdAt, Long postId, UserResponseDTO user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.postId = postId;
        this.user = user;
    }

    public static CommentResponseDTO fromEntity(Comment comment) {
        Long postId = comment.getPost() != null ? comment.getPost().getId() : null;
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                postId,
                UserResponseDTO.fromEntity(comment.getUser())
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
