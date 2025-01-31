package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.validateService.LoginDto;
import com.crater.craterlogin.bean.entity.db.UserData;
import com.crater.craterlogin.dao.TokenDao;
import com.crater.craterlogin.dao.db.UserDataDao;
import com.crater.craterlogin.exception.AccountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class ValidateServiceImplTest {
    private final ValidateServiceImpl testTarget = new ValidateServiceImpl();
    private PasswordEncoder passwordEncoder;
    private UserDataDao userDataDao;

    @BeforeEach
    public void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDataDao = Mockito.mock(UserDataDao.class);
        TokenDao tokenDao = Mockito.mock(TokenDao.class);
        Long loginTokenTimeout = 1000L;
        testTarget.setPasswordEncoder(passwordEncoder);
        testTarget.setUserDataDao(userDataDao);
        testTarget.setTokenDao(tokenDao);
        testTarget.setLoginTokenTimeout(loginTokenTimeout);
    }

    @Test
    public void login_accountNotFound_throwAccountException() {
        Mockito.when(userDataDao.select(Mockito.any())).thenReturn(new ArrayList<>());
        var exception = Assertions.assertThrows(AccountException.class, () -> testTarget.login(new LoginDto("string", "string")));
        Assertions.assertEquals("account not found", exception.getMessage());
    }

    @Test
    public void login_invalidPassword_throwAccountException() {
        Mockito.when(userDataDao.select(Mockito.any())).thenReturn(List.of(new UserData().setUserName("string").setPassword("string")));
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        var exception = Assertions.assertThrows(AccountException.class, () -> testTarget.login(new LoginDto("string", "string")));
        Assertions.assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    public void login_success() {
        var queryResult = new ArrayList<UserData>();
        queryResult.add(new UserData().setUserName("string").setPassword("string"));
        Mockito.when(userDataDao.select(Mockito.any())).thenReturn(queryResult);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Assertions.assertNotNull(testTarget.login(new LoginDto("string", "string")));
    }
}
