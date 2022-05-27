package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * User dto for admin
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserDtoForAdmin extends RepresentationModel<UserDtoForAdmin> {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private boolean isBlocked;
}
