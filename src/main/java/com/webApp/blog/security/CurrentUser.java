package com.webApp.blog.security;

import com.webApp.blog.exception.ResourceNotFoundException;
import com.webApp.blog.repository.CommentRepository;
import com.webApp.blog.repository.PostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CurrentUser {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CurrentUser(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public void requireSelfOrAdmin(Long userId) {
        if (isAdmin()) {
            return;
        }
        if (!Objects.equals(currentUserId(), userId)) {
            throw new AccessDeniedException("You can only perform this action for your own account");
        }
    }

    public Long resolveUserId(Long requestedUserId) {
        if (isAdmin() && requestedUserId != null) {
            return requestedUserId;
        }
        return currentUserId();
    }

    public void requirePostOwnerOrAdmin(Long postId) {
        if (isAdmin()) {
            return;
        }
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        if (!postRepository.existsByIdAndAuthor_Id(postId, currentUserId())) {
            throw new AccessDeniedException("You can only modify your own posts");
        }
    }

    public void requireCommentOwnerOrAdmin(Long commentId) {
        if (isAdmin()) {
            return;
        }
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
        if (!commentRepository.existsByIdAndUser_Id(commentId, currentUserId())) {
            throw new AccessDeniedException("You can only delete your own comments");
        }
    }

    private Long currentUserId() {
        return principal().getId();
    }

    private boolean isAdmin() {
        return principal().getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    private UserPrincipal principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
            throw new AccessDeniedException("Authentication is required");
        }
        return userPrincipal;
    }
}
