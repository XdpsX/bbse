package com.bbse.identity.service.impl;

import com.bbse.identity.constant.MessageCode;
import com.bbse.identity.dto.LoginRequest;
import com.bbse.identity.dto.RegisterRequest;
import com.bbse.identity.dto.TokenResponse;
import com.bbse.identity.exception.DuplicateException;
import com.bbse.identity.exception.NotFoundException;
import com.bbse.identity.model.Role;
import com.bbse.identity.model.User;
import com.bbse.identity.repository.RoleRepository;
import com.bbse.identity.repository.UserRepository;
import com.bbse.identity.security.TokenProvider;
import com.bbse.identity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse register(RegisterRequest request) {
        User user = userRepository.findByEmail(request.email()).orElse(null);
        if (user != null) {
            throw new DuplicateException(MessageCode.EMAIL_DUPLICATED, request.email());
        }
        Role role = roleRepository.findById(Role.USER)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        User newUser = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        User savedUser = userRepository.save(newUser);
        return new TokenResponse(
                tokenProvider.generateAccessToken(savedUser),
                tokenProvider.generateRefreshToken(savedUser)
        );
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();
        return new TokenResponse(
                tokenProvider.generateAccessToken(user),
                tokenProvider.generateRefreshToken(user)
        );
    }
}
