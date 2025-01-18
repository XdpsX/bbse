package com.bbse.identity.service;

import com.bbse.identity.dto.LoginRequest;
import com.bbse.identity.dto.RegisterRequest;
import com.bbse.identity.dto.TokenResponse;

public interface AuthService {
    TokenResponse register(RegisterRequest request);
    TokenResponse login(LoginRequest request);
}
