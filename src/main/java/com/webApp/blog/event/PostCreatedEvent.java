package com.webApp.blog.event;

import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;

public record PostCreatedEvent(
        Long id,
        String title,
        String category,
        String contentPreview,
        String authorUsername,
        String authorEmail,
        String authorName,
        java.time.LocalDateTime createdAt
) {

    public PostCreatedEvent(Long id, String title, String category, String contentPreview,
                            String authorUsername, String authorEmail) {
        this(id, title, category, contentPreview, authorUsername, authorEmail, authorUsername, null);
    }

    public static PostCreatedEvent from(Post post) {
        User author = post.getAuthor();
        return new PostCreatedEvent(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                preview(post.getContent()),
                author == null ? "" : author.getUsername(),
                author == null ? "" : author.getEmail(),
                author == null || author.getName() == null ? author == null ? "" : author.getUsername() : author.getName(),
                post.getCreatedAt()
        );
    }

    private static String preview(String value) {
        if (value == null || value.length() <= 500) {
            return value;
        }
        return value.substring(0, 500) + "...";
    }
}
