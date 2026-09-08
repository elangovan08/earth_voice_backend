package com.webApp.blog.controller;

import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import com.webApp.blog.security.CurrentUser;
import com.webApp.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CurrentUser currentUser;
    private final Validator validator;

    public PostController(PostService postService, CurrentUser currentUser, Validator validator) {
        this.postService = postService;
        this.currentUser = currentUser;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long id) {
        byte[] imageData = postService.getPostImageData(id);
        if (imageData == null || imageData.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String contentType = postService.getPostImageContentType(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : MediaType.IMAGE_PNG_VALUE))
                .body(imageData);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO request) {
        request.setAuthorId(currentUser.resolveUserId(request.getAuthorId()));
        validateRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDTO> createPostWithImage(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(defaultValue = "General") String category,
            @RequestParam Long authorId,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        authorId = currentUser.resolveUserId(authorId);

        PostRequestDTO request = new PostRequestDTO();
        request.setTitle(title);
        request.setContent(content);
        request.setCategory(category);
        request.setAuthorId(authorId);
        request.setImageUrl(imageUrl);
        populateImageData(request, image);
        validateRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @RequestBody PostRequestDTO request) {
        currentUser.requirePostOwnerOrAdmin(id);
        request.setAuthorId(currentUser.resolveUserId(request.getAuthorId()));
        validateRequest(request);
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDTO> updatePostWithImage(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(defaultValue = "General") String category,
            @RequestParam Long authorId,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        currentUser.requirePostOwnerOrAdmin(id);
        authorId = currentUser.resolveUserId(authorId);

        PostRequestDTO request = new PostRequestDTO();
        request.setTitle(title);
        request.setContent(content);
        request.setCategory(category);
        request.setAuthorId(authorId);
        request.setImageUrl(imageUrl);
        populateImageData(request, image);
        validateRequest(request);

        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        currentUser.requirePostOwnerOrAdmin(id);
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPosts(keyword));
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<PostResponseDTO> likePost(@PathVariable Long id,
                                                    @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(postService.likePost(id, currentUser.resolveUserId(userId)));
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<PostResponseDTO> unlikePost(@PathVariable Long id,
                                                      @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(postService.unlikePost(id, currentUser.resolveUserId(userId)));
    }

    @PostMapping("/{id}/bookmarks")
    public ResponseEntity<PostResponseDTO> bookmarkPost(@PathVariable Long id,
                                                        @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(postService.bookmarkPost(id, currentUser.resolveUserId(userId)));
    }

    @DeleteMapping("/{id}/bookmarks")
    public ResponseEntity<PostResponseDTO> unbookmarkPost(@PathVariable Long id,
                                                          @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(postService.unbookmarkPost(id, currentUser.resolveUserId(userId)));
    }

    private void validateRequest(PostRequestDTO request) {
        Set<ConstraintViolation<PostRequestDTO>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<PostRequestDTO> violation : violations) {
                if (!message.isEmpty()) message.append("; ");
                message.append(violation.getPropertyPath()).append(": ").append(violation.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
        }
    }

    private void populateImageData(PostRequestDTO request, MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return;
        }

        String contentType = image.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only image uploads are supported");
        }

        try {
            request.setImageData(image.getBytes());
            request.setImageContentType(contentType);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to store image", ex);
        }
    }
}
