package com.webApp.blog.event;

import com.webApp.blog.model.User;

public record UserCreatedEvent(
        Long id,
        String username,
        String email,
        String role,
        String name,
        java.time.LocalDateTime registeredAt,
        String ipAddress
) {

    public UserCreatedEvent(Long id, String username, String email, String role) {
        this(id, username, email, role, username, null, null);
    }

    public static UserCreatedEvent from(User user) {
        return from(user, null);
    }

    public static UserCreatedEvent from(User user, String ipAddress) {
        return new UserCreatedEvent(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getName() == null ? user.getUsername() : user.getName(),
                user.getCreatedAt(),
                ipAddress
        );
    }
}
