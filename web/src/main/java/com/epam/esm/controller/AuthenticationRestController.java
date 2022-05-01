package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceNotAuthorized;
import com.epam.esm.exception.ServiceValidException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.domain.AuthRequest;
import com.epam.esm.security.domain.AuthResponse;
import com.epam.esm.security.domain.RefreshTokenRequest;
import com.epam.esm.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.epam.esm.exception.MessageException.USER_LOGIN_NOT_FILLED;
import static com.epam.esm.exception.MessageException.USER_NOT_AUTHORIZED;
import static com.epam.esm.exception.MessageException.USER_PASSWORD_NOT_FILLED;
import static com.epam.esm.exception.MessageException.USER_PASSWORD_NOT_MATCH;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * Authenticate user
     *
     * @param request - user request data
     * @return - user response data
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse authenticateUser(@RequestBody AuthRequest request) {
        Optional<User> user = userRepository.findByLogin(request.getLogin());
        String password = userRepository.findUserPasswordByLogin(request.getLogin());
        boolean isResult = bCryptPasswordEncoder.matches(request.getPassword(), password);

        if (request.getLogin() == null || request.getLogin().isEmpty()) {
            log.error("Login was not filled");
            throw new ServiceValidException(USER_LOGIN_NOT_FILLED);
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            log.error("Password was not filled");
            throw new ServiceValidException(USER_PASSWORD_NOT_FILLED);
        }

        if (!isResult && user.isPresent()) {
            log.error("Password was not correct");
            throw new ServiceValidException(USER_PASSWORD_NOT_MATCH);
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return AuthResponse.builder()
                .login(request.getLogin())
                .role(String.valueOf(authenticate.getAuthorities()).toLowerCase())
                .accessToken(jwtUtil.generateToken(userDetailsService.loadUserByUsername(request.getLogin())))
                .refreshToken(jwtUtil.generateRefreshToken(userDetailsService.loadUserByUsername(request.getLogin())))
                .build();
    }

    /**
     * Token refresh
     *
     * @param refreshTokenRequest - token request data
     * @return - user response data
     */
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        try {
            jwtUtil.getUsernameFromRefreshToken(refreshToken);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | IllegalArgumentException ex) {
            log.error("User was not authorized." + ex.getMessage());
            throw new ServiceNotAuthorized(USER_NOT_AUTHORIZED);
        }

        String userNameFromRefreshToken = jwtUtil.getUsernameFromRefreshToken(refreshToken);
        Optional<User> user = userRepository.findByLogin(userNameFromRefreshToken);
        String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user.get().getLogin()));

        return AuthResponse.builder()
                .login(user.get().getLogin())
                .role(String.valueOf(user.get().getRoles()).toLowerCase())
                .accessToken(token)
                .build();
    }
}
