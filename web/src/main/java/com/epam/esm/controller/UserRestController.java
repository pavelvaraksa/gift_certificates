package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    public final UserService userService;
    private final ModelMapper modelMapper;

    /**
     * Find list of users
     *
     * @return - page of users or empty page
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> findAllUsers(Pageable pageable, @RequestParam(value = "isDeleted",
            required = false, defaultValue = "false") boolean isDeleted) {
        List<User> listUser = userService.findAll(pageable, isDeleted);
        List<UserDto> items = new ArrayList<>();

        for (User user : listUser) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.add(linkTo(methodOn(UserRestController.class).findUserById(user.getId())).withSelfRel(),
                    linkTo(UserRestController.class).slash("search?login=" + user.getLogin()).withSelfRel());
            items.add(userDto);
        }

        return CollectionModel.of(items, linkTo(UserRestController.class).withRel("users"));
    }

    /**
     * Find user by id
     *
     * @param id - user id
     * @return - user
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return EntityModel.of(modelMapper.map(user.get(), UserDto.class),
                linkTo(UserRestController.class).slash(id).withSelfRel(),
                linkTo(UserRestController.class).slash("search?login=" + user.get().getLogin()).withSelfRel(),
                linkTo(UserRestController.class).withRel("users"));
    }

    /**
     * Find user by login
     *
     * @param login - user login
     * @return - user
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> findUserByLogin(@RequestParam(value = "login", required = false) String login) {
        Optional<User> user = userService.findByLogin(login);
        return EntityModel.of(modelMapper.map(user.get(), UserDto.class),
                linkTo(UserRestController.class).slash(user.get().getId()).withSelfRel(),
                linkTo(UserRestController.class).slash("search?login=" + user.get().getLogin()).withSelfRel(),
                linkTo(UserRestController.class).withRel("users"));
    }

    /**
     * Create user
     *
     * @param user - user
     * @return - user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> saveUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return EntityModel.of(modelMapper.map(newUser, UserDto.class),
                linkTo(UserRestController.class).slash(newUser.getId()).withSelfRel(),
                linkTo(UserRestController.class).slash("search?login=" + newUser.getLogin()).withSelfRel(),
                linkTo(UserRestController.class).withRel("users"));
    }

    /**
     * Update user by id
     *
     * @param id   - user id
     * @param user - user
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateById(id, user);
        return EntityModel.of(modelMapper.map(updatedUser, UserDto.class),
                linkTo(UserRestController.class).slash(updatedUser.getId()).withSelfRel(),
                linkTo(UserRestController.class).slash("search?login=" + updatedUser.getLogin()).withSelfRel(),
                linkTo(UserRestController.class).withRel("users"));
    }

    /**
     * Delete user by id
     *
     * @param id - user id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
