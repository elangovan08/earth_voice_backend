package com.webApp.blog.service;

import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<PostResponseDTO> getAllPosts();

    PostResponseDTO getPostById(Long id);

    PostResponseDTO createPost(PostRequestDTO request);

    PostResponseDTO updatePost(Long id, PostRequestDTO request);

    void deletePost(Long id);

    byte[] getPostImageData(Long id);

    String getPostImageContentType(Long id);

    List<PostResponseDTO> searchPosts(String keyword);

    Page<PostResponseDTO> getPostsPage(String keyword, Pageable pageable);

    PostResponseDTO likePost(Long postId, Long userId);

    PostResponseDTO unlikePost(Long postId, Long userId);

    PostResponseDTO bookmarkPost(Long postId, Long userId);

    PostResponseDTO unbookmarkPost(Long postId, Long userId);
}
