package com.epam.esm.dto;

import lombok.Data;

import java.util.List;

/**
 * User dto
 */
@Data
public class UserDto {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private List<OrderDto> order;
}
