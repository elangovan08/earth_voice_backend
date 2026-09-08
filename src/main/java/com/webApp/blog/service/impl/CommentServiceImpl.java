package com.webApp.blog.service.impl;

import com.webApp.blog.dto.request.CommentRequestDTO;
import com.webApp.blog.dto.response.CommentResponseDTO;
import com.webApp.blog.event.CommentCreatedEvent;
import com.webApp.blog.exception.ResourceNotFoundException;
import com.webApp.blog.model.Comment;
import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;
import com.webApp.blog.repository.CommentRepository;
import com.webApp.blog.repository.PostRepository;
import com.webApp.blog.repository.UserRepository;
import com.webApp.blog.service.CommentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<CommentResponseDTO> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(CommentResponseDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public CommentResponseDTO addComment(Long postId, CommentRequestDTO request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        eventPublisher.publishEvent(CommentCreatedEvent.from(savedComment));
        return CommentResponseDTO.fromEntity(savedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
