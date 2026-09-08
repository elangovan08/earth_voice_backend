package com.webApp.blog.dto.response;

import com.webApp.blog.model.User;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String role;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, String email, String role) {
        this(id, username, username, email, role);
    }

    public UserResponseDTO(Long id, String name, String username, String email, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public static UserResponseDTO fromEntity(User user) {
        if (user == null) {
            return null;
        }
        String name = user.getName() == null || user.getName().isBlank()
                ? user.getUsername()
                : user.getName();
        return new UserResponseDTO(user.getId(), name, user.getUsername(), user.getEmail(), user.getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
