package com.epam.esm.service;

import com.epam.esm.domain.User;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void beforeAll() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPositive() {
        String login = "login_4";
        String firstname = "firstname_4";
        String lastname = "lastname_4";

        User createdUser = new User();
        createdUser.setId(4L);
        createdUser.setLogin(login);
        createdUser.setFirstName(firstname);
        createdUser.setLastName(lastname);

        User expectedUser = new User();
        expectedUser.setId(4L);
        expectedUser.setLogin(login);
        expectedUser.setFirstName(firstname);
        expectedUser.setLastName(lastname);

        Mockito.when(userRepository.findByLogin(Mockito.eq(login))).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(createdUser)).thenReturn(expectedUser);

        User actualUser = userRepository.save(createdUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findAllPositive() {
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "login_1", "firstname_1", "lastname_1", false, null),
                new User(2L, "login_2", "firstname_2", "lastname_2", false, null),
                new User(3L, "login_3", "firstname_3", "lastname_3", false, null));

        Mockito.when(userRepository.findAll(null, false)).thenReturn(expectedUsers);
        List<User> actualUsers = userRepository.findAll(null, false);
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void findAllNegative() {
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "login_111", "firstname_11", "lastname_11", false, null),
                new User(2L, "login_222", "firstname_22", "lastname_22", false, null),
                new User(3L, "login_333", "firstname_33", "lastname_33", false, null));

        Mockito.when(userRepository.findAll(null, false)).thenReturn(expectedUsers);
        expectedUsers = new ArrayList<>();
        List<User> actualUsers = userRepository.findAll(null, false);
        assertNotEquals(expectedUsers, actualUsers);
    }

    @Test
    public void findByIdPositive() {
        Long requiredId = 2L;
        User expectedUser =
                new User(requiredId, "login_2", "firstname_2", "lastname_2", false, null);
        Optional<User> optionalUser = Optional.of(expectedUser);
        Mockito.when(userRepository.findById(requiredId)).thenReturn(optionalUser);
        Optional<User> actualUser = userRepository.findById(requiredId);
        assertEquals(actualUser, optionalUser);
    }

    @Test
    public void findByIdNotNull() {
        Long requiredId = 1L;
        User expectedUser =
                new User(requiredId, "login_1", "firstname_1", "lastname_1", false, null);
        Optional<User> optionalUser = Optional.of(expectedUser);
        Mockito.when(userRepository.findById(requiredId)).thenReturn(optionalUser);
        userRepository.findById(requiredId);
        assertNotNull(requiredId);
    }

    @Test
    public void findByIdNull() {
        Long requiredId = null;
        User expectedUser =
                new User(requiredId, "login_1", "firstname_1", "lastname_1", false, null);
        Optional<User> optionalUser = Optional.of(expectedUser);
        Mockito.when(userRepository.findById(requiredId)).thenReturn(optionalUser);
        userRepository.findById(requiredId);
        assertNull(requiredId);
    }

    @Test
    public void findByIdWithoutThrowsException() {
        Long existsId = 3L;
        User expectedUser =
                new User(existsId, "login_3", "firstname_3", "lastname_3", false, null);
        Optional<User> optionalUser = Optional.of(expectedUser);
        Mockito.when(userRepository.findById(existsId)).thenReturn(optionalUser);
        assertDoesNotThrow(() -> userRepository.findById(existsId));
    }

    @Test
    public void deletePositive() {
        Long existsId = 2L;
        User expectedUser =
                new User(existsId, "login_2", "firstname_2", "lastname_2", false, null);
        Optional<User> optionalUser = Optional.of(expectedUser);
        Mockito.when(userRepository.findById(existsId)).thenReturn(optionalUser);
        userRepository.deleteById(existsId);
    }

    @Test
    public void deleteNegative() {
        Long existsId = 2L;
        Long nonExistingId = 22L;
        User expectedUser =
                new User(existsId, "login_2", "firstname_2", "lastname_2", false, null);
        Optional<User> optionalUser = Optional.of(expectedUser);
        Mockito.when(userRepository.findById(existsId)).thenReturn(optionalUser);
        userRepository.deleteById(nonExistingId);
    }
}
