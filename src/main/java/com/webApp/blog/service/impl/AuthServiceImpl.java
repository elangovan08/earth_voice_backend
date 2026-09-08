package com.webApp.blog.service.impl;

import com.webApp.blog.dto.request.LoginRequestDTO;
import com.webApp.blog.dto.request.SignupRequestDTO;
import com.webApp.blog.dto.response.AuthResponseDTO;
import com.webApp.blog.dto.response.UserResponseDTO;
import com.webApp.blog.event.UserCreatedEvent;
import com.webApp.blog.event.UserLoggedInEvent;
import com.webApp.blog.exception.DuplicateResourceException;
import com.webApp.blog.exception.InvalidCredentialsException;
import com.webApp.blog.model.User;
import com.webApp.blog.repository.UserRepository;
import com.webApp.blog.security.JwtService;
import com.webApp.blog.service.AuthService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public AuthResponseDTO signup(SignupRequestDTO request, String ipAddress) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(StringUtils.hasText(request.getName()) ? request.getName().trim() : request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("POSTER");

        User savedUser = userRepository.save(user);
        eventPublisher.publishEvent(UserCreatedEvent.from(savedUser, ipAddress));
        return buildAuthResponse("Signup successful", savedUser);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        eventPublisher.publishEvent(UserLoggedInEvent.from(user));
        return buildAuthResponse("Login successful", user);
    }

    private AuthResponseDTO buildAuthResponse(String message, User user) {
        return new AuthResponseDTO(
                message,
                UserResponseDTO.fromEntity(user),
                jwtService.generateToken(user),
                jwtService.getExpirationSeconds()
        );
    }
}
