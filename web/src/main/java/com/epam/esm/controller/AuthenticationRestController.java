package com.epam.esm.controller;

import com.epam.esm.security.domain.AuthRequest;
import com.epam.esm.security.domain.AuthResponse;
import com.epam.esm.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * Authenticate user
     *
     * @param request - user request data
     * @return - user response data
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse authenticateUser(@RequestBody AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return AuthResponse.builder()
                .login(request.getLogin())
                .role(String.valueOf(authenticate.getAuthorities()).toLowerCase())
                .token(jwtUtil.generateToken(userDetailsService.loadUserByUsername(request.getLogin())))
                .build();
    }
}
