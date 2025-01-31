package com.crater.craterlogin.controller;

import com.crater.craterlogin.bean.dto.validateService.LoginResultDto;
import com.crater.craterlogin.bean.request.validateController.LoginRequest;
import com.crater.craterlogin.exception.RequestFormatException;
import com.crater.craterlogin.service.ValidateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ValidateControllerTest {
    private final ValidateController testTarget = new ValidateController();
    private ValidateService validateService;

    @BeforeEach
    public void setUp() {
        validateService = Mockito.mock( ValidateService.class);
        testTarget.setValidateService(validateService);
    }

    @Test
    public void login_inputNull_throwRequestFormatException() {
        var exception = Assertions.assertThrows(RequestFormatException.class, () -> testTarget.login(null));
        Assertions.assertEquals("login request is null", exception.getMessage());
    }

    @Test
    public void login_userNameNullPasswordNull_throwRequestFormatException() {
        var exception = Assertions.assertThrows(RequestFormatException.class, () -> testTarget.login(new LoginRequest(null, null)));
        Assertions.assertEquals("username is null, password is null", exception.getMessage());
    }

    @Test
    public void login_success() {
        var serviceResult = new LoginResultDto("token");
        Mockito.when(validateService.login(Mockito.any())).thenReturn(serviceResult);
        var result = testTarget.login(new LoginRequest("username", "password"));
        Assertions.assertEquals(serviceResult.token(), result.token());
    }
}
