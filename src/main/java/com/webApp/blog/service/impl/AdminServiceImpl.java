package com.webApp.blog.service.impl;

import com.webApp.blog.dto.response.AdminDashboardResponseDTO;
import com.webApp.blog.dto.response.UserResponseDTO;
import com.webApp.blog.repository.CommentRepository;
import com.webApp.blog.repository.ContactRepository;
import com.webApp.blog.repository.PostRepository;
import com.webApp.blog.repository.UserRepository;
import com.webApp.blog.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ContactRepository contactRepository;

    public AdminServiceImpl(
            UserRepository userRepository,
            PostRepository postRepository,
            CommentRepository commentRepository,
            ContactRepository contactRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public AdminDashboardResponseDTO getDashboard() {
        return new AdminDashboardResponseDTO(
                userRepository.count(),
                postRepository.count(),
                commentRepository.count(),
                contactRepository.count(),
                userRepository.findTop5ByOrderByIdDesc().stream()
                        .map(UserResponseDTO::fromEntity)
                        .toList()
        );
    }
}
