package org.is.iscourse.controller;

import org.is.iscourse.model.dto.AuthRequest;
import org.is.iscourse.model.dto.AuthResponse;
import org.is.iscourse.model.entity.User;
import org.is.iscourse.security.JwtService;
import org.is.iscourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        String token = jwtService.generateToken(user.getUsername());
        userService.updateLastLogin(user);

        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), user.getEmail(), user.getFullName()));
    }
}
