package com.webApp.blog.controller;

import com.webApp.blog.constants.ApiPaths;
import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.ApiResponseDTO;
import com.webApp.blog.dto.response.PageResponseDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import com.webApp.blog.security.CurrentUser;
import com.webApp.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.V1_POSTS)
public class V1PostController {

    private final PostService postService;
    private final CurrentUser currentUser;

    public V1PostController(PostService postService, CurrentUser currentUser) {
        this.postService = postService;
        this.currentUser = currentUser;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<PageResponseDTO<PostResponseDTO>>> getPosts(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(page, Math.min(size, 30), Sort.by(sortDirection, sortBy));
        PageResponseDTO<PostResponseDTO> response = PageResponseDTO.from(postService.getPostsPage(q, pageable));

        return ResponseEntity.ok(ApiResponseDTO.success("Posts fetched successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PostResponseDTO>> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseDTO.success("Post fetched successfully", postService.getPostById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PostResponseDTO>> createPost(@Valid @RequestBody PostRequestDTO request) {
        currentUser.requireSelfOrAdmin(request.getAuthorId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Post created successfully", postService.createPost(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PostResponseDTO>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDTO request
    ) {
        currentUser.requirePostOwnerOrAdmin(id);
        currentUser.requireSelfOrAdmin(request.getAuthorId());
        return ResponseEntity.ok(ApiResponseDTO.success("Post updated successfully", postService.updatePost(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePost(@PathVariable Long id) {
        currentUser.requirePostOwnerOrAdmin(id);
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Post deleted successfully", null));
    }
}
