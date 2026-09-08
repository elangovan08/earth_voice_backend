package com.webApp.blog.event;

import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;

public record PostUpdatedEvent(
        Long postId,
        String newTitle,
        String previousTitle,
        String updaterUsername,
        String updaterName,
        String updaterEmail,
        java.time.LocalDateTime updatedAt
) {

    public PostUpdatedEvent(Long postId, String newTitle, String previousTitle, String updaterUsername) {
        this(postId, newTitle, previousTitle, updaterUsername, updaterUsername, null, null);
    }

    public static PostUpdatedEvent from(Post post, String previousTitle) {
        User author = post.getAuthor();
        return new PostUpdatedEvent(
                post.getId(),
                post.getTitle(),
                previousTitle,
                author == null ? "" : author.getUsername(),
                author == null || author.getName() == null ? author == null ? "" : author.getUsername() : author.getName(),
                author == null ? "" : author.getEmail(),
                java.time.LocalDateTime.now()
        );
    }
}
