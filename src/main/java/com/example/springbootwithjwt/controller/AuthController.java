package com.example.springbootwithjwt.controller;

import com.example.springbootwithjwt.config.JwtUtil;
import com.example.springbootwithjwt.dto.AuthResponse;
import com.example.springbootwithjwt.dto.UserDto;
import com.example.springbootwithjwt.entity.User;
import com.example.springbootwithjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setProvider("LOCAL");
        userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, "User registered successfully!");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );
        String token = jwtUtil.generateToken(userDto.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, "Login successful!"));
    }
}
