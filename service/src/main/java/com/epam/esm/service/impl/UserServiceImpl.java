package com.epam.esm.service.impl;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceExistException;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.MessageException.TAG_NOT_FOUND;
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

    @Override
    public List<User> findAll(Pageable pageable, boolean isDeleted) {
        return userRepository.findAll(pageable, isDeleted);
    }

    @Override
    public List<Long> findAllForWidelyUsedTag() {
        List<Long> listId = userRepository.findAllForWidelyUsedTag();

        if (listId.isEmpty()) {
            log.error("Tag was not found");
            throw new ServiceNotFoundException(TAG_NOT_FOUND);
        }

        return listId;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User with id " + id + " was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        if (user.get().isActive()) {
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

        if (user.get().isActive()) {
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    public User save(User user) {
        UserValidator.isUserValid(user);
        String login = user.getLogin();
        Optional<User> userLogin = userRepository.findByLogin(login);

        if (userLogin.isPresent()) {
            log.error("User with login " + user.getLogin() + " already exist");
            throw new ServiceExistException(USER_EXIST);
        }

        log.info("User with login  " + user.getLogin() + " saved");
        return userRepository.save(user);
    }

    @Override
    public User updateById(Long id, User user) {
        Optional<User> userById = userRepository.findById(id);

        if (userById.isEmpty()) {
            log.error("User was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        if (userById.get().isActive()) {
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        Optional<User> userByLogin = userRepository.findByLogin(user.getLogin());

        if (userByLogin.isPresent()) {
            log.error("User with login " + user.getLogin() + " already exist");
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

        UserValidator.isUserValid(user);
        user.setId(userById.get().getId());
        userRepository.updateById(user);
        Optional<User> updatedUserById = userRepository.findById(user.getId());

        log.info("User with login " + user.getLogin() + " updated");
        return updatedUserById.get();
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error("User was not found");
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        if (user.get().isActive()) {
            throw new ServiceNotFoundException(USER_NOT_FOUND);
        }

        log.info("User with id " + id + " deleted");
        userRepository.deleteById(id);
    }
}
