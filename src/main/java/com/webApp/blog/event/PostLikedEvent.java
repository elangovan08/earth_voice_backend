package com.webApp.blog.event;

import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;

public record PostLikedEvent(
        Long postId,
        String postTitle,
        String postAuthorUsername,
        String postAuthorName,
        String likerUsername,
        String likerEmail,
        java.time.LocalDateTime likedAt
) {

    public PostLikedEvent(Long postId, String postTitle, String postAuthorUsername,
                          String likerUsername, String likerEmail) {
        this(postId, postTitle, postAuthorUsername, postAuthorUsername, likerUsername, likerEmail, null);
    }

    public static PostLikedEvent from(Post post, User user) {
        return new PostLikedEvent(
                post.getId(),
                post.getTitle(),
                post.getAuthor() == null ? "" : post.getAuthor().getUsername(),
                post.getAuthor() == null || post.getAuthor().getName() == null
                        ? post.getAuthor() == null ? "" : post.getAuthor().getUsername()
                        : post.getAuthor().getName(),
                user.getUsername(),
                user.getEmail(),
                java.time.LocalDateTime.now()
        );
    }
}
