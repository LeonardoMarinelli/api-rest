package com.ecommerce.api_rest.controller;

import com.ecommerce.api_rest.dto.auth.AuthResponseDto;
import com.ecommerce.api_rest.dto.user.UserLoginDto;
import com.ecommerce.api_rest.dto.user.UserRegisterDto;
import com.ecommerce.api_rest.entity.enums.Role;
import com.ecommerce.api_rest.entity.User;
import com.ecommerce.api_rest.jwt.JwtUtil;
import com.ecommerce.api_rest.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody @Valid UserRegisterDto dto) {

        User user = userService.registerNewUser(dto.getUsername(), dto.getPassword());

        var authorities = user.getRoles()
                .stream()
                .map(Role::name)
                .toList();

        String token = jwtUtil.generateToken(user.getUsername(), authorities);

        return ResponseEntity.ok(new AuthResponseDto(token));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody @Valid UserLoginDto dto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        var authorities = ((org.springframework.security.core.userdetails.User) auth.getPrincipal())
                .getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList();

        String token = jwtUtil.generateToken(dto.getUsername(), authorities);

        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
