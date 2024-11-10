package com.crater.craterlogin.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationProviderImplTest {
    private final AuthenticationProviderImpl testTarget = new AuthenticationProviderImpl();
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDetailsService = Mockito.mock(UserDetailsService.class);
        testTarget.setPasswordEncoder(passwordEncoder);
        testTarget.setUserDetailsService(userDetailsService);
    }

    @Test
    public void authenticate_userNamePasswordNull_throwAuthenticationServiceException() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(null);
        Mockito.when(authentication.getCredentials()).thenReturn("");
        var exception = Assertions.assertThrows(AuthenticationServiceException.class, () -> testTarget.authenticate(authentication));
        Assertions.assertEquals("Username or password is missing", exception.getMessage());
    }

    @Test
    public void authenticate_userNotFound_throwAuthenticationServiceException() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("string");
        Mockito.when(authentication.getCredentials()).thenReturn("string");
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.any())).thenReturn(null);
        var exception = Assertions.assertThrows(AuthenticationServiceException.class, () -> testTarget.authenticate(authentication));
        Assertions.assertEquals("user not found", exception.getMessage());
    }

    @Test
    public void authenticate_invalidPassword_throwAuthenticationServiceException() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("string");
        Mockito.when(authentication.getCredentials()).thenReturn("string");
        var userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getPassword()).thenReturn("string");
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.any())).thenReturn(userDetails);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        var exception = Assertions.assertThrows(AuthenticationServiceException.class, () -> testTarget.authenticate(authentication));
        Assertions.assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    public void authenticate_success() {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("string");
        Mockito.when(authentication.getCredentials()).thenReturn("string");
        var userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getPassword()).thenReturn("string");
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.any())).thenReturn(userDetails);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Assertions.assertNotNull(testTarget.authenticate(authentication));
    }
}
