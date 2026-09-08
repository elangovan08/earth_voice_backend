package com.webApp.blog.service.impl;

import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import com.webApp.blog.event.*;
import com.webApp.blog.exception.ResourceNotFoundException;
import com.webApp.blog.mapper.PostMapper;
import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;
import com.webApp.blog.repository.PostRepository;
import com.webApp.blog.repository.UserRepository;
import com.webApp.blog.service.GeneratedImage;
import com.webApp.blog.service.ImageGenerationService;
import com.webApp.blog.service.PostService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final ImageGenerationService imageGenerationService;
    private final ApplicationEventPublisher eventPublisher;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper,
                           ImageGenerationService imageGenerationService, ApplicationEventPublisher eventPublisher) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.imageGenerationService = imageGenerationService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        return postMapper.toDetailResponse(findPostWithDetails(id));
    }

    @Override
    @Transactional
    public PostResponseDTO createPost(PostRequestDTO request) {
        User author = findUser(request.getAuthorId());
        if (!StringUtils.hasText(request.getImageUrl()) && !hasImageData(request)) {
            GeneratedImage generated = imageGenerationService.generateImage(request.getTitle(), request.getContent(), request.getCategory());
            if (generated != null && generated.imageData() != null && generated.imageData().length > 0) {
                request.setImageData(generated.imageData());
                request.setImageContentType(generated.contentType());
            }
        }

        Post post = postMapper.toEntity(request, author);
        Post savedPost = postRepository.save(post);
        setEndpointImageUrl(savedPost);
        eventPublisher.publishEvent(PostCreatedEvent.from(savedPost));
        return postMapper.toDetailResponse(savedPost);
    }

    @Override
    @Transactional
    public PostResponseDTO updatePost(Long id, PostRequestDTO request) {
        Post post = findPost(id);
        String previousTitle = post.getTitle();
        User author = findUser(request.getAuthorId());
        boolean alreadyHasImage = (post.getImageData() != null && post.getImageData().length > 0) || StringUtils.hasText(post.getImageUrl());

        if (!StringUtils.hasText(request.getImageUrl()) && !hasImageData(request) && !alreadyHasImage) {
            GeneratedImage generated = imageGenerationService.generateImage(request.getTitle(), request.getContent(), request.getCategory());
            if (generated != null && generated.imageData() != null && generated.imageData().length > 0) {
                request.setImageData(generated.imageData());
                request.setImageContentType(generated.contentType());
            }
        }

        postMapper.updateEntity(post, request, author);
        Post updatedPost = postRepository.save(post);
        setEndpointImageUrl(updatedPost);
        eventPublisher.publishEvent(PostUpdatedEvent.from(updatedPost, previousTitle));
        return postMapper.toDetailResponse(updatedPost);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Post post = findPost(id);
        String postTitle = post.getTitle();
        String deleterUsername = post.getAuthor() != null ? post.getAuthor().getUsername() : "";
        String deleterName = post.getAuthor() == null || post.getAuthor().getName() == null
                ? deleterUsername : post.getAuthor().getName();
        String deleterEmail = post.getAuthor() != null ? post.getAuthor().getEmail() : "";
        postRepository.deleteById(id);
        eventPublisher.publishEvent(new PostDeletedEvent(
                id, postTitle, deleterUsername, deleterName, deleterEmail, java.time.LocalDateTime.now()));
    }

    @Override
    public byte[] getPostImageData(Long id) {
        return findPost(id).getImageData();
    }

    @Override
    public String getPostImageContentType(Long id) {
        return findPost(id).getImageContentType();
    }

    private boolean hasImageData(PostRequestDTO request) {
        return request.getImageData() != null && request.getImageData().length > 0;
    }

    private void setEndpointImageUrl(Post post) {
        if (post.getImageData() != null && post.getImageData().length > 0) {
            String endpoint = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/posts/")
                    .path(post.getId().toString())
                    .path("/image")
                    .toUriString();
            post.setImageUrl(endpoint);
        }
    }

    @Override
    public List<PostResponseDTO> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(postMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public Page<PostResponseDTO> getPostsPage(String keyword, Pageable pageable) {
        Page<Post> posts = StringUtils.hasText(keyword)
                ? postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable)
                : postRepository.findAll(pageable);

        return posts.map(postMapper::toSummaryResponse);
    }

    @Override
    @Transactional
    public PostResponseDTO likePost(Long postId, Long userId) {
        Post post = findPost(postId);
        User user = findUser(userId);

        if (post.getLikedBy().add(user)) {
            post.setLikeCount(post.getLikedBy().size());
            eventPublisher.publishEvent(PostLikedEvent.from(post, user));
        }

        return postMapper.toDetailResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponseDTO unlikePost(Long postId, Long userId) {
        Post post = findPost(postId);
        User user = findUser(userId);
        if (post.getLikedBy().remove(user)) {
            post.setLikeCount(post.getLikedBy().size());
        }
        return postMapper.toDetailResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponseDTO bookmarkPost(Long postId, Long userId) {
        Post post = findPost(postId);
        User user = findUser(userId);
        if (postRepository.countBookmark(postId, userId) == 0) {
            postRepository.addBookmark(postId, userId);
            eventPublisher.publishEvent(PostBookmarkedEvent.from(post, user));
        }
        return postMapper.toDetailResponse(findPostWithDetails(postId), postRepository.countBookmarksByPostId(postId));
    }

    @Override
    @Transactional
    public PostResponseDTO unbookmarkPost(Long postId, Long userId) {
        findPost(postId);
        findUser(userId);
        postRepository.removeBookmark(postId, userId);
        return postMapper.toDetailResponse(findPostWithDetails(postId), postRepository.countBookmarksByPostId(postId));
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    private Post findPostWithDetails(Long id) {
        return postRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
