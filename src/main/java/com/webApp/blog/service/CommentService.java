package com.webApp.blog.service;

import com.webApp.blog.dto.request.CommentRequestDTO;
import com.webApp.blog.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    List<CommentResponseDTO> getCommentsByPost(Long postId);

    CommentResponseDTO addComment(Long postId, CommentRequestDTO request);

    void deleteComment(Long commentId);
}
