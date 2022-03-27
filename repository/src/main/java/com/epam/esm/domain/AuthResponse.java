package com.epam.esm.domain;

import lombok.Data;

@Data
public class AuthResponse {
    private String login;

    private String token;
}
