package com.webApp.blog.dto.response;

public class AuthResponseDTO {

    private String message;
    private UserResponseDTO user;
    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresIn;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String message, UserResponseDTO user) {
        this.message = message;
        this.user = user;
    }

    public AuthResponseDTO(String message, UserResponseDTO user, String accessToken, long expiresIn) {
        this.message = message;
        this.user = user;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
