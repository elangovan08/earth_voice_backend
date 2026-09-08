package com.webApp.blog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification", indexes = {
        @Index(name = "idx_notification_created_at", columnList = "created_at"),
        @Index(name = "idx_notification_is_read_created_at", columnList = "is_read, created_at")
})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String message;

    @Column(nullable = false, length = 80)
    private String type;

    @Column(length = 255)
    private String username;

    @Column(length = 320)
    private String userEmail;

    @Column(name = "related_entity_id")
    private Long relatedEntityId;

    @Column(name = "related_entity_type", length = 80)
    private String relatedEntityType;

    @Column(name = "is_read", nullable = false)
    private boolean read = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Notification() {
    }

    public Notification(String title, String message, String type, String username, String userEmail,
                        Long relatedEntityId, String relatedEntityType) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.username = username;
        this.userEmail = userEmail;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
