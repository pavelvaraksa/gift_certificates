package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import java.util.Optional;
import java.util.stream.Collectors;

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
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> findAllUsers(Pageable pageable, @RequestParam(value = "isDeleted",
            required = false, defaultValue = "false") boolean isDeleted) {

        Page<User> listUser = userService.findAll(pageable, isDeleted);
        return new PageImpl<>(listUser
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList()));
    }

    /**
     * Find user by id
     *
     * @param id - user id
     * @return - user
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return modelMapper.map(user.get(), UserDto.class);
    }

    /**
     * Find user by login
     *
     * @param login - user login
     * @return - user
     */
    @GetMapping("/search/login/{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByLogin(@PathVariable String login) {
        Optional<User> user = userService.findByLogin(login);
        return modelMapper.map(user.get(), UserDto.class);
    }

    /**
     * Create user
     *
     * @param user - user
     * @return - user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return modelMapper.map(newUser, UserDto.class);
    }

    /**
     * Update user by id
     *
     * @param id   - user id
     * @param user - user
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateById(id, user);
        return modelMapper.map(updatedUser, UserDto.class);
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
