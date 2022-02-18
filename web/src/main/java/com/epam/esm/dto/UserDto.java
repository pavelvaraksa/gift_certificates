package com.epam.esm.dto;

import lombok.Data;

/**
 * User dto.
 */
@Data
public class UserDto {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;
}
