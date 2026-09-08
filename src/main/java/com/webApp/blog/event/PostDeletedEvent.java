package com.webApp.blog.event;

public record PostDeletedEvent(
        Long postId,
        String postTitle,
        String deleterUsername,
        String deleterName,
        String deleterEmail,
        java.time.LocalDateTime deletedAt
    ) {

    public PostDeletedEvent(Long postId, String postTitle, String deleterUsername) {
        this(postId, postTitle, deleterUsername, deleterUsername, null, null);
    }
}
