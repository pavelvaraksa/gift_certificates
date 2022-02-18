package com.epam.esm.service.impl;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User service implementation.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User with id " + id + " was not found");
            throw new ServiceNotFoundException("not found");
        }

        return user;
    }

    @Override
    public Optional<User> findByName(String name) {
        Optional<User> user = userRepository.findByName(name);

        if (user.isEmpty()) {
            log.error("User with name " + name + " was not found");
            throw new ServiceNotFoundException("not found");
        }

        return user;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isEmpty()) {
            log.error("User with login " + login + " was not found");
            throw new ServiceNotFoundException("not found");
        }

        return user;
    }

    @Override
    public User save(User user) {
        String login = user.getLogin();
        Optional<User> userLogin = userRepository.findByLogin(login);

        if (userLogin.isPresent()) {
            log.error("User with login " + user.getLogin() + " already exist");
            throw new ServiceExistException("exist");
        }

        log.info("User with login  " + user.getLogin() + " saved");
        return userRepository.save(user);
    }

    @Override
    public User updateById(Long id, User user) {
        Optional<User> userById = userRepository.findById(id);

        if (userById.isEmpty()) {
            log.error("User was not found");
            throw new ServiceNotFoundException("not found");
        }

        Optional<User> userByLogin = userRepository.findByLogin(user.getLogin());

        if (userByLogin.isPresent()) {
            log.error("User with login " + user.getLogin() + " already exist");
            throw new ServiceExistException("exist");
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

        user.setId(userById.get().getId());
        userRepository.updateById(user);

        log.info("User with login " + user.getLogin() + " updated");
        return user;
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User was not found");
            throw new ServiceNotFoundException("not found");
        }

        log.info("User with id " + id + " deleted");
        userRepository.deleteById(id);
    }
}
