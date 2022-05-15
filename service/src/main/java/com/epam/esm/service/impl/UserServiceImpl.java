package com.epam.esm.service.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.Role;
import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.exception.MessageException.USER_EXIST;
import static com.epam.esm.exception.MessageException.USER_NOT_FOUND;

/**
 * User service implementation
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User with id " + id + " was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isEmpty()) {
            log.error("User with login " + login + " was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        return user;
    }

    @Transactional
    @Override
    public User save(User user) {
        UserValidator.isUserValid(user);
        String login = user.getLogin();
        Optional<User> userLogin = userRepository.findByLogin(login);

        if (userLogin.isPresent()) {
            log.error("User with login " + login + " already exists");
            throw new ServiceExistException(USER_EXIST);
        }

        Optional<Role> roleUser = roleRepository.findById(2L);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleUser.get()));
        log.info("User with login " + user.getLogin() + " saved");
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateById(Long id, User user) {
        Optional<User> userById = userRepository.findById(id);

        if (userById.isEmpty()) {
            log.error("User was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        Optional<User> userByLogin = userRepository.findByLogin(user.getLogin());

        if (userByLogin.isPresent()) {
            log.error("User with login " + user.getLogin() + " already exists");
            throw new ServiceExistException(USER_EXIST);
        }

        if (user.getLogin() == null) {
            user.setLogin(userById.get().getLogin());
        }

        if (user.getFirstName() == null) {
            user.setFirstName(userById.get().getFirstName());
        }

        if (user.getLastName() == null) {
            user.setLastName(userById.get().getLastName());
        }

        if (user.getPassword() == null) {
            user.setPassword("password");
        }

        UserValidator.isUserValid(user);

        if (user.getPassword().equals("password")) {
            user.setPassword(userById.get().getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        user.setId(userById.get().getId());
        userRepository.updateById(user.getLogin(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getId());
        Set<Order> orderSet = orderRepository.findAllByUserId(user.getId());
        user.setOrder(orderSet);
        log.info("User with login " + user.getLogin() + " updated");
        return user;
    }

    @Transactional
    @Override
    public User deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        log.info("User with id " + id + " deleted");
        userRepository.deleteById(id);
        return user.get();
    }
}
