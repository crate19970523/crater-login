package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.editAccountService.AddAccountDto;
import com.crater.craterlogin.bean.entity.db.UserData;
import com.crater.craterlogin.bean.entity.db.UserKyc;
import com.crater.craterlogin.dao.db.UserDataDao;
import com.crater.craterlogin.dao.db.UserKycDao;
import com.crater.craterlogin.exception.AccountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

class EditAccountServiceImplTest {
    private final EditAccountServiceImpl testTarget = new EditAccountServiceImpl();
    private UserDataDao userDataDao;
    private UserKycDao userKycDao;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userDataDao = Mockito.mock(UserDataDao.class);
        userKycDao = Mockito.mock(UserKycDao.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        testTarget.setUserDataDao(userDataDao);
        testTarget.setUserKycDao(userKycDao);
        testTarget.setPasswordEncoder(passwordEncoder);
    }

    @Test
    public void editAccount_dbHaveSameUserName_fail() {
        final var userName = "string";
        final var password = "password";
        final var email = "email@gmail.com";
        var addAccountDto = new AddAccountDto(userName, password, email);
        Mockito.when(userDataDao.select(Mockito.any())).thenAnswer(i -> {
            var userData = i.getArgument(0, UserData.class);
            Assertions.assertEquals(userName, userData.userName());
            return List.of(new UserData("aaa", "bbb", true, null, null, null, null));
        });
        var exception = Assertions.assertThrows(AccountException.class, () -> testTarget.addAccount(addAccountDto));
        Assertions.assertEquals("found same user name", exception.getMessage());
    }

    @Test
    public void editAccount_success() {
        final var userName = "string";
        final var password = "password";
        final var encodedPassword = "pppppppaaaa";
        final var email = "email@gmail.com";
        var addAccountDto = new AddAccountDto(userName, password, email);
        Mockito.when(userDataDao.select(Mockito.any())).thenAnswer(i -> {
            var userData = i.getArgument(0, UserData.class);
            Assertions.assertEquals(userName, userData.userName());
            return new ArrayList<>();
        });
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenAnswer(i -> {
            var passwordFormTestTarget = i.getArgument(0, String.class);
            Assertions.assertEquals(password, passwordFormTestTarget);
            return encodedPassword;
        });
        Mockito.doAnswer(i -> {
            var userData = i.getArgument(0, UserData.class);
            Assertions.assertEquals(userName, userData.userName());
            Assertions.assertEquals(encodedPassword, userData.password());
            return null;
        }).when(userDataDao).insert(Mockito.any());
        Mockito.doAnswer(i -> {
            var userKyc = i.getArgument(0, UserKyc.class);
            Assertions.assertEquals(email, userKyc.email());
            Assertions.assertEquals(userName, userKyc.userName());
            return null;
        }).when(userKycDao).insert(Mockito.any());
        testTarget.addAccount(addAccountDto);
    }

}