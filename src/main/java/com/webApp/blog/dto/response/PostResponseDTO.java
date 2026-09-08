package com.webApp.blog.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String imageUrl;
    private LocalDateTime createdAt;
    private int likeCount;
    private int bookmarkCount;
    private UserResponseDTO author;
    private List<CommentResponseDTO> comments;

    public PostResponseDTO() {
    }

    public PostResponseDTO(
            Long id,
            String title,
            String content,
            String category,
            String imageUrl,
            LocalDateTime createdAt,
            int likeCount,
            int bookmarkCount,
            UserResponseDTO author,
            List<CommentResponseDTO> comments
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.bookmarkCount = bookmarkCount;
        this.author = author;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public UserResponseDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserResponseDTO author) {
        this.author = author;
    }

    public List<CommentResponseDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDTO> comments) {
        this.comments = comments;
    }
}
