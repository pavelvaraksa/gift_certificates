package com.epam.esm.security.config;

import com.epam.esm.security.exception.CustomAccessDeniedHandler;
import com.epam.esm.security.exception.CustomEntryPoint;
import com.epam.esm.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class that provides JWT based Spring Security application
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtFilter jwtFilter;

    //permissions for admin
    private static final String CREATE_CERTIFICATE = "/certificates";
    private static final String CREATE_TAG = "/tags";
    private static final String FIND_ALL_CERTIFICATES = "/certificates/active";
    private static final String FIND_ALL_TAGS = "/tags/active";
    private static final String FIND_ALL_ORDERS = "/orders";
    private static final String ACTIVATE_OR_DELETE_ORDER = "/orders/**";
    private static final String FIND_ALL_USERS = "/users";
    private static final String BLOCKED_USER = "/users/blocked/**";

    //permissions for user or admin
    private static final String MAKE_ORDER = "/orders";
    private static final String FIND_ORDER_BY_ID = "/orders/**";
    private static final String ALL_REQUESTS_FOR_TAGS = "/tags/**";
    private static final String FIND_USER_BY_ID_OR_LOGIN_OR_UPDATE_OR_DELETE = "/users/**";

    //permissions for guest
    private static final String LOG_IN = "/auth/login";
    private static final String SIGN_UP = "/registrate";
    private static final String ALL_REQUESTS_FOR_CERTIFICATES = "/certificates/**";
    private static final String REFRESH_TOKEN = "/auth/refresh";

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //permissions for guest, user or admin
                .antMatchers(LOG_IN).permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP).permitAll()
                .antMatchers(HttpMethod.GET, FIND_ALL_CERTIFICATES).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, ALL_REQUESTS_FOR_CERTIFICATES).permitAll()
                .antMatchers(REFRESH_TOKEN).permitAll()
                //permissions for user or admin
                .antMatchers(HttpMethod.GET, FIND_ALL_USERS).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, FIND_ALL_ORDERS).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, BLOCKED_USER).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, FIND_ALL_TAGS).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, ALL_REQUESTS_FOR_TAGS).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, MAKE_ORDER).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, FIND_ORDER_BY_ID).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, FIND_USER_BY_ID_OR_LOGIN_OR_UPDATE_OR_DELETE).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PATCH, FIND_USER_BY_ID_OR_LOGIN_OR_UPDATE_OR_DELETE).hasAnyRole("USER", "ADMIN")
                //permissions for admin
                .antMatchers(HttpMethod.POST, CREATE_CERTIFICATE).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, ALL_REQUESTS_FOR_CERTIFICATES).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, ALL_REQUESTS_FOR_CERTIFICATES).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, ACTIVATE_OR_DELETE_ORDER).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, CREATE_TAG).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, ALL_REQUESTS_FOR_TAGS).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, ALL_REQUESTS_FOR_TAGS).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, FIND_USER_BY_ID_OR_LOGIN_OR_UPDATE_OR_DELETE).hasRole("ADMIN")
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
