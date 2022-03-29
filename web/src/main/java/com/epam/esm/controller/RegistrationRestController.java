package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registrate")
@RequiredArgsConstructor
public class RegistrationRestController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registration(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userService.save(user);

        Map<String, Object> result = new HashMap<>();

        result.put("id", newUser.getId());
        result.put("login", newUser.getLogin());

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
