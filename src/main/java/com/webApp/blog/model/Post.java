package com.webApp.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content is required")
    @Column(length = 10000, nullable = false)
    private String content;

    @Column(length = 80)
    private String category = "General";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "image_content_type", length = 100)
    private String imageContentType;

    // ================= Author =================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    // ================= Image Path =================
    // ================= Like Count =================
    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Formula("(select count(*) from post_bookmarks pb where pb.post_id = id)")
    private int bookmarkCount;

    // ================= Comments =================
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // ================= Liked By =================
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();

    // ================= Bookmarked By =================
    @ManyToMany
    @JoinTable(
            name = "post_bookmarks",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> bookmarkedBy = new HashSet<>();

    // ================= Timestamp Set Automatically =================
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ========== Getters & Setters ==========
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getLikeCount() {
        return likeCount == null ? 0 : likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }

    public Set<User> getBookmarkedBy() {
        return bookmarkedBy;
    }

    public void setBookmarkedBy(Set<User> bookmarkedBy) {
        this.bookmarkedBy = bookmarkedBy;
    }
}
