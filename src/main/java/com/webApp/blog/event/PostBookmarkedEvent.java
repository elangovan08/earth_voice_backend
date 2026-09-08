package com.webApp.blog.event;

import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;

public record PostBookmarkedEvent(
        Long postId,
        String postTitle,
        String bookmarkUsername,
        String bookmarkName,
        String bookmarkEmail,
        java.time.LocalDateTime bookmarkedAt
) {

    public PostBookmarkedEvent(Long postId, String postTitle, String bookmarkUsername, String bookmarkEmail) {
        this(postId, postTitle, bookmarkUsername, bookmarkUsername, bookmarkEmail, null);
    }

    public static PostBookmarkedEvent from(Post post, User user) {
        return new PostBookmarkedEvent(
                post.getId(),
                post.getTitle(),
                user.getUsername(),
                user.getName() == null ? user.getUsername() : user.getName(),
                user.getEmail(),
                java.time.LocalDateTime.now()
        );
    }
}
