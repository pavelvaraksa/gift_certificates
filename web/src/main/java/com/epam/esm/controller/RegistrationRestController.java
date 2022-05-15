package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/registrate")
@RequiredArgsConstructor
public class RegistrationRestController {
    public final UserService userService;
    private final ModelMapper modelMapper;

    /**
     * Registrate user
     *
     * @param user - user
     * @return - user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> saveUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return EntityModel.of(modelMapper.map(newUser, UserDto.class),
                linkTo(methodOn(UserRestController.class).findUserById(newUser.getId())).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(newUser.getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(newUser.getId(), newUser)).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(newUser.getId())).withRel("delete by id"));
    }
}
