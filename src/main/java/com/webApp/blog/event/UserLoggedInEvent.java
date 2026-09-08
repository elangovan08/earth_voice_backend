package com.webApp.blog.event;

import com.webApp.blog.model.User;

import java.time.LocalDateTime;

public record UserLoggedInEvent(
        Long id,
        String username,
        String name,
        String email,
        String role,
        LocalDateTime loggedInAt
) {

    public static UserLoggedInEvent from(User user) {
        return new UserLoggedInEvent(
                user.getId(),
                user.getUsername(),
                user.getName() == null ? user.getUsername() : user.getName(),
                user.getEmail(),
                user.getRole(),
                LocalDateTime.now()
        );
    }
}
