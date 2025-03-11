package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.LoginResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private LoginService loginService;

    private User user;

    @BeforeEach
    void setUp() {
        loginService.setEncoder(passwordEncoder);
        loginService.setJwtEncoder(jwtEncoder);
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");
        user.setRoles(Set.of(Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build()));

    }

    @Test
    void shouldLoginSuccessfully() {
        LoginReqDto loginReq = new LoginReqDto("user@example.com", "password");
        when(userService.findByEmail(loginReq.email())).thenReturn(user);
        when(passwordEncoder.matches(loginReq.password(), user.getPassword())).thenReturn(true);

        Jwt mockJwt = mock(Jwt.class);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);
        when(mockJwt.getTokenValue()).thenReturn("valid token");

        LoginResDto response = loginService.login(loginReq);

        assertNotNull(response);
        assertNotNull(response.token());
        assertEquals(300L, response.expiresIn());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        LoginReqDto loginReq = new LoginReqDto("user@example.com", "wrongpassword");
        when(userService.findByEmail(loginReq.email())).thenReturn(user);
        when(passwordEncoder.matches(loginReq.password(), user.getPassword())).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> loginService.login(loginReq));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        LoginReqDto loginReq = new LoginReqDto("unknown@example.com", "password");
        when(userService.findByEmail(loginReq.email())).thenThrow(new ObjectNotFoundException("User not found"));

        assertThrows(BadCredentialsException.class, () -> loginService.login(loginReq));
    }
}