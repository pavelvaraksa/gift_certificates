//package com.epam.esm.controller;
//
//import com.epam.esm.domain.User;
//import com.epam.esm.exception.ServiceNotAuthorized;
//import com.epam.esm.security.util.JwtUtil;
//import com.epam.esm.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//import static com.epam.esm.exception.MessageException.USER_NOT_AUTHORIZED;
//
//@Log4j2
//@RestController
//@RequestMapping("/logout")
//@RequiredArgsConstructor
//public class LogoutRestController {
//    private final UserDetailsService userDetailsService;
//    private final UserService userService;
//
//    /**
//     * Logout user
//     */
//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public void logout() {
//        Optional<User> searchUser = userService.findById(id);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(searchUser.get().getLogin());
//
//        if (!userDetails.isAccountNonExpired()) {
//            log.error("User was not authorized");
//            throw new ServiceNotAuthorized(USER_NOT_AUTHORIZED);
//        }
//
//        boolean result = !userDetails.isAccountNonExpired();
//    }
//}
