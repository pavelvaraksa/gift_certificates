package com.epam.esm.security.service;

import com.epam.esm.domain.User;
import com.epam.esm.exception.ServiceNotFoundException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.MessageException.USER_NOT_FOUND;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceProvider implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        Optional<User> searchUser = userRepository.findByLogin(login);

        if (searchUser.isPresent()) {
            String userLogin = searchUser.get().getLogin();
            String userPassword = searchUser.get().getPassword();
            List<GrantedAuthority> authorityList = new ArrayList<>();
            String role = roleRepository.findRoleByUserId(searchUser.get().getId());
            authorityList.add(new SimpleGrantedAuthority(role));
            return new org.springframework.security.core.userdetails.User(userLogin, userPassword, authorityList);
        }

        log.error("User was not found");
        throw new ServiceNotFoundException(USER_NOT_FOUND);
    }
}