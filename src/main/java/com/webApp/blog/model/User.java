package com.webApp.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Username is required")
    private String username;

    @Column(length = 255)
    private String name;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonIgnore
    private String password;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "likedBy")
    @JsonIgnore
    private Set<Post> likedPosts = new HashSet<>();

    @ManyToMany(mappedBy = "bookmarkedBy")
    @JsonIgnore
    private Set<Post> bookmarkedPosts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<Post> getLikedPosts() { return likedPosts; }

    public void setLikedPosts(Set<Post> likedPosts) { this.likedPosts = likedPosts; }

    public Set<Post> getBookmarkedPosts() { return bookmarkedPosts; }

    public void setBookmarkedPosts(Set<Post> bookmarkedPosts) { this.bookmarkedPosts = bookmarkedPosts; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }
}
