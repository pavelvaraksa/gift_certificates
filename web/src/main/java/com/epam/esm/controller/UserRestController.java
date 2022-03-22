package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
     * Find users with pagination, sorting and info about deleted users
     *
     * @return - list of users or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> findAllUsers() {
        List<User> listUser = userService.findAll();
        List<UserDto> items = new ArrayList<>();

        for (User user : listUser) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.add(linkTo(methodOn(UserRestController.class).findUserById(user.getId())).withRel("find by id"),
                    linkTo(methodOn(UserRestController.class).findUserByLogin(user.getLogin())).withRel("find by login"),
                    linkTo(methodOn(UserRestController.class).updateUser(user.getId(), user)).withRel("update by id"),
                    linkTo(methodOn(UserRestController.class).deleteUser(user.getId())).withRel("delete by id"));
            items.add(userDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(UserRestController.class)
                .findAllUsers()).withRel("find all users"));
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
                linkTo(methodOn(UserRestController.class).findUserById(id)).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(user.get().getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(id, user.get())).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(id)).withRel("delete by id"));
    }

    /**
     * Find user by login
     *
     * @param login - user login
     * @return - user
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> findUserByLogin(@RequestParam(value = "login") String login) {
        Optional<User> user = userService.findByLogin(login);
        return EntityModel.of(modelMapper.map(user.get(), UserDto.class),
                linkTo(methodOn(UserRestController.class).findUserById(user.get().getId())).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(user.get().getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(user.get().getId(), user.get())).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(user.get().getId())).withRel("delete by id"));
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
                linkTo(methodOn(UserRestController.class).findUserById(newUser.getId())).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(newUser.getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(newUser.getId(), newUser)).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(newUser.getId())).withRel("delete by id"));
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
                linkTo(methodOn(UserRestController.class).findUserById(id)).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(updatedUser.getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(id, updatedUser)).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(id)).withRel("delete by id"));
    }

    /**
     * Activate user by id
     *
     * @param id        - user id
     * @param isCommand - command for activate
     */
    @PatchMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> activateGiftCertificate(@PathVariable Long id,
                                                        @RequestParam(value = "isCommand", defaultValue = "false") boolean isCommand) {
        User activatedUser = userService.activateById(id, isCommand);
        return EntityModel.of(modelMapper.map(activatedUser, UserDto.class),
                linkTo(methodOn(UserRestController.class).findUserById(id)).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(activatedUser.getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(id, activatedUser)).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(id)).withRel("delete by id"));
    }

    /**
     * Delete user by id
     *
     * @param id - user id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> deleteUser(@PathVariable Long id) {
        User deletedUser = userService.deleteById(id);
        return EntityModel.of(modelMapper.map(deletedUser, UserDto.class));
    }
}
