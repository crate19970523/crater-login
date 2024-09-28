package com.crater.craterlogin.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            var username = authentication.getName();
            var password = authentication.getCredentials().toString();
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                throw new AuthenticationServiceException("Username or password is missing");
            }
            var userData = userDetailsService.loadUserByUsername(username);
            if (userData == null) {
                throw new AuthenticationServiceException("user not found");
            }
            if (!passwordEncoder.matches(password, userData.getPassword())) {
                throw new AuthenticationServiceException("Invalid password");
            }
            return new UsernamePasswordAuthenticationToken(username, password, userData.getAuthorities());
        } catch (Exception e) {
            log.error("authentication failed", e);
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
