package com.webApp.blog.mapper;

import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.CommentResponseDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import com.webApp.blog.dto.response.UserResponseDTO;
import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class PostMapper {

    public PostResponseDTO toDetailResponse(Post post) {
        List<CommentResponseDTO> comments = post.getComments().stream()
                .map(CommentResponseDTO::fromEntity)
                .toList();
        return toResponse(post, comments);
    }

    public PostResponseDTO toDetailResponse(Post post, long bookmarkCount) {
        List<CommentResponseDTO> comments = post.getComments().stream()
                .map(CommentResponseDTO::fromEntity)
                .toList();
        return toResponse(post, comments, Math.toIntExact(bookmarkCount));
    }

    public PostResponseDTO toSummaryResponse(Post post) {
        return toResponse(post, List.of());
    }

    public Post toEntity(PostRequestDTO request, User author) {
        Post post = new Post();
        updateEntity(post, request, author);
        return post;
    }

    public void updateEntity(Post post, PostRequestDTO request, User author) {
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(normalizeCategory(request.getCategory()));
        post.setAuthor(author);

        if (request.getImageUrl() != null) {
            post.setImageUrl(request.getImageUrl());
        }
        if (request.getImageData() != null) {
            post.setImageData(request.getImageData());
        }
        if (request.getImageContentType() != null) {
            post.setImageContentType(request.getImageContentType());
        }
    }

    private PostResponseDTO toResponse(Post post, List<CommentResponseDTO> comments) {
        return toResponse(post, comments, post.getBookmarkCount());
    }

    private PostResponseDTO toResponse(Post post, List<CommentResponseDTO> comments, int bookmarkCount) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getLikeCount(),
                bookmarkCount,
                UserResponseDTO.fromEntity(post.getAuthor()),
                comments
        );
    }

    private String normalizeCategory(String category) {
        return StringUtils.hasText(category) ? category.trim() : "General";
    }
}
