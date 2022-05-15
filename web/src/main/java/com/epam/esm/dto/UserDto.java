package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * User dto
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserDto extends RepresentationModel<UserDto> {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;
}
