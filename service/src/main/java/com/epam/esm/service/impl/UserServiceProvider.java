package com.epam.esm.service.impl;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceProvider implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        try {
            Optional<User> searchUser = userRepository.findByLogin(login);
            if (searchUser.isPresent()) {
                String userLogin = searchUser.get().getLogin();
                String userPassword = searchUser.get().getPassword();
                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(searchUser.get().getRole()));
                return new org.springframework.security.core.userdetails.User(userLogin, userPassword, authorityList);
            } else {
                String errorMessage = "Can't find user with login " + login;
                log.error(errorMessage);
                throw new SecurityException(errorMessage);
            }
        } catch (ServiceNotFoundException e) {
            throw new SecurityException("Security exception while trying to find user." + e.getMessage());
        }
    }
}
