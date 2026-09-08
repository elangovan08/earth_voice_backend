package com.webApp.blog.dto.response;

import com.webApp.blog.model.Notification;

import java.time.LocalDateTime;

public class NotificationResponseDTO {

    private Long id;
    private String title;
    private String message;
    private String type;
    private String username;
    private String userEmail;
    private Long relatedEntityId;
    private String relatedEntityType;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationResponseDTO() {
    }

    public NotificationResponseDTO(Long id, String title, String message, String type, String username,
                                   String userEmail, Long relatedEntityId, String relatedEntityType,
                                   boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.username = username;
        this.userEmail = userEmail;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
        this.read = read;
        this.createdAt = createdAt;
    }

    public static NotificationResponseDTO fromEntity(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getUsername(),
                notification.getUserEmail(),
                notification.getRelatedEntityId(),
                notification.getRelatedEntityType(),
                notification.isRead(),
                notification.getCreatedAt()
        );
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
