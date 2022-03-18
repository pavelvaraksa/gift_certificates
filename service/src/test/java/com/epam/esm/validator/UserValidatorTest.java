package com.epam.esm.validator;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserValidatorTest {
    private User user;

    @BeforeEach
    public void beforeAll() {
        user = new User();
        user.setLogin("Login");
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
    }

    @Test
    public void testCorrectName() {
        Assertions.assertTrue(UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLogin() {
        user.setFirstName("Login  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectFirstname() {
        user.setFirstName("Firstname  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLastname() {
        user.setFirstName("Lastname  1");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLoginSymbols() {
        user.setFirstName("+++");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectFirstnameSymbols() {
        user.setFirstName("###");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLastnameSymbols() {
        user.setFirstName("&&&");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLoginMaxSize() {
        user.setLogin("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectFirstnameMaxSize() {
        user.setFirstName("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLastnameMaxSize() {
        user.setFirstName("1111111111111111111111111111111");
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLoginNull() {
        user.setLogin(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectFirstnameNull() {
        user.setFirstName(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }

    @Test
    public void testIncorrectLastnameNull() {
        user.setLastName(null);
        Assertions.assertThrows(ServiceValidException.class,
                () -> UserValidator.isUserValid(user));
    }
}
