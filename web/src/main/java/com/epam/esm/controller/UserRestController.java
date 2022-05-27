package com.epam.esm.controller;

import com.epam.esm.domain.User;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserDtoForAdmin;
import com.epam.esm.exception.ServiceForbiddenException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.MessageException.USER_RESOURCE_FORBIDDEN;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    public final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    /**
     * Find all users
     *
     * @return - list of users or empty list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDtoForAdmin> findAllUsers(@PageableDefault(sort = {"id"}) Pageable pageable) {
        List<User> listUser = userService.findAll(pageable);
        List<UserDtoForAdmin> items = new ArrayList<>();

        for (User user : listUser) {
            UserDtoForAdmin userDto = modelMapper.map(user, UserDtoForAdmin.class);
            userDto.add(linkTo(methodOn(UserRestController.class).findUserById(user.getId())).withRel("find by id"),
                    linkTo(methodOn(UserRestController.class).findUserByLogin(user.getLogin())).withRel("find by login"),
                    linkTo(methodOn(UserRestController.class).updateUser(user.getId(), user)).withRel("update by id"),
                    linkTo(methodOn(UserRestController.class).deleteUser(user.getId())).withRel("delete by id"));
            items.add(userDto);
        }

        return CollectionModel.of(items, linkTo(methodOn(UserRestController.class)
                .findAllUsers(pageable)).withRel("find all users"));
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Optional<User> searchUser;

        if (role.equals("ROLE_USER") && user.get().getId().equals(id)) {
            searchUser = userService.findById(id);
            return takeHateoasForUser(searchUser.get());
        } else if (role.equals("ROLE_ADMIN")) {
            searchUser = userService.findById(id);
            return takeHateoasForAdmin(searchUser.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(user.get().getId());
        Optional<User> searchUser;

        if (role.equals("ROLE_USER") && user.get().getLogin().equals(login)) {
            searchUser = userService.findByLogin(login);
            return takeHateoasForUser(searchUser.get());
        } else if (role.equals("ROLE_ADMIN")) {
            searchUser = userService.findByLogin(login);
            return takeHateoasForAdmin(searchUser.get());
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> searchUser = userService.findByLogin(currentPrincipalName);
        String role = roleRepository.findRoleByUserId(searchUser.get().getId());
        User updatedUser;

        if (role.equals("ROLE_USER") && searchUser.get().getId().equals(id)) {
            updatedUser = userService.updateById(id, user);
            return takeHateoasForUser(updatedUser);
        } else if (role.equals("ROLE_ADMIN")) {
            updatedUser = userService.updateById(id, user);
            return takeHateoasForAdmin(updatedUser);
        } else {
            throw new ServiceForbiddenException(USER_RESOURCE_FORBIDDEN);
        }
    }

    /**
     * Blocked user by id
     *
     * @param id - user id
     */
    @PatchMapping("/blocked/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean blockedUser(@PathVariable Long id) {
        return userService.blockedById(id);
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

    private EntityModel<UserDto> takeHateoasForAdmin(User user) {
        return EntityModel.of(modelMapper.map(user, UserDto.class),
                linkTo(methodOn(UserRestController.class).findUserById(user.getId())).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(user.getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(user.getId(), user)).withRel("update by id"),
                linkTo(methodOn(UserRestController.class).deleteUser(user.getId())).withRel("delete by id"));
    }

    private EntityModel<UserDto> takeHateoasForUser(User user) {
        return EntityModel.of(modelMapper.map(user, UserDto.class),
                linkTo(methodOn(UserRestController.class).findUserById(user.getId())).withRel("find by id"),
                linkTo(methodOn(UserRestController.class).findUserByLogin(user.getLogin())).withRel("find by login"),
                linkTo(methodOn(UserRestController.class).updateUser(user.getId(), user)).withRel("update by id"));
    }
}
