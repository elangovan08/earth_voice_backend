package com.webApp.blog.dto.response;

public class ApiMessageResponseDTO {

    private String message;

    public ApiMessageResponseDTO() {
    }

    public ApiMessageResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
