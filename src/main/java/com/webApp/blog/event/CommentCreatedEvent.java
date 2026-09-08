package com.webApp.blog.event;

import com.webApp.blog.model.Comment;
import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;

public record CommentCreatedEvent(
        Long commentId,
        String commentContent,
        Long postId,
        String postTitle,
        String commenterUsername,
        String commenterName,
        String commenterEmail,
        java.time.LocalDateTime commentedAt
) {

    public CommentCreatedEvent(Long commentId, String commentContent, Long postId, String postTitle,
                               String commenterUsername, String commenterEmail) {
        this(commentId, commentContent, postId, postTitle, commenterUsername, commenterUsername, commenterEmail, null);
    }

    public static CommentCreatedEvent from(Comment comment) {
        Post post = comment.getPost();
        User user = comment.getUser();
        return new CommentCreatedEvent(
                comment.getId(),
                comment.getContent(),
                post == null ? null : post.getId(),
                post == null ? "" : post.getTitle(),
                user == null ? "" : user.getUsername(),
                user == null || user.getName() == null ? user == null ? "" : user.getUsername() : user.getName(),
                user == null ? "" : user.getEmail(),
                comment.getCreatedAt()
        );
    }
}
