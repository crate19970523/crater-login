package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.entity.db.UserData;
import com.crater.craterlogin.dao.db.UserDataDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImplTest {
    private final UserDetailsServiceImpl testTarget = new UserDetailsServiceImpl();
    private UserDataDao userDataDao;

    @BeforeEach
    public void setUp() {
        userDataDao = Mockito.mock(UserDataDao.class);
        testTarget.setUserDataDao(userDataDao);
    }

    @Test
    public void loadUserByUsername_userNotFound_returnNull() {
        Mockito.when(userDataDao.select(Mockito.any())).thenReturn(new ArrayList<>());
        Assertions.assertNull(testTarget.loadUserByUsername("string"));
    }

    @Test
    public void loadUserByUsername_success() {
        var userData = new UserData();
        userData.setUserName("string");
        userData.setPassword("string");
        Mockito.when(userDataDao.select(Mockito.any())).thenReturn(List.of(new UserData().setUserName("string").setPassword("string")));
        var result = testTarget.loadUserByUsername("string");
        Assertions.assertEquals("admin", result.getAuthorities().toArray()[0].toString());
        Assertions.assertEquals("string", result.getUsername());
        Assertions.assertEquals("string", result.getPassword());
    }
}
