package com.epam.esm.domain;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;

    private String password;
}
