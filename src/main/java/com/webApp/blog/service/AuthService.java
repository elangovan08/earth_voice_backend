package com.webApp.blog.service;

import com.webApp.blog.dto.request.LoginRequestDTO;
import com.webApp.blog.dto.request.SignupRequestDTO;
import com.webApp.blog.dto.response.AuthResponseDTO;

public interface AuthService {

    default AuthResponseDTO signup(SignupRequestDTO request) {
        return signup(request, null);
    }

    AuthResponseDTO signup(SignupRequestDTO request, String ipAddress);

    AuthResponseDTO login(LoginRequestDTO request);
}
