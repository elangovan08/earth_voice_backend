package com.webApp.blog.controller;

import com.webApp.blog.dto.request.CommentRequestDTO;
import com.webApp.blog.dto.response.CommentResponseDTO;
import com.webApp.blog.security.CurrentUser;
import com.webApp.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.web.server.ResponseStatusException;
import java.util.Set;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final CurrentUser currentUser;
    private final Validator validator;

    public CommentController(CommentService commentService, CurrentUser currentUser, Validator validator) {
        this.commentService = commentService;
        this.currentUser = currentUser;
        this.validator = validator;
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDTO request
    ) {
        request.setUserId(currentUser.resolveUserId(request.getUserId()));
        Set<ConstraintViolation<CommentRequestDTO>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, violations.iterator().next().getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(postId, request));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        currentUser.requireCommentOwnerOrAdmin(commentId);
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
