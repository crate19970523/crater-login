package com.crater.craterlogin.controller;

import com.crater.craterlogin.bean.dto.validateService.LoginDto;
import com.crater.craterlogin.bean.request.validateController.LoginRequest;
import com.crater.craterlogin.bean.response.Status;
import com.crater.craterlogin.bean.response.validateResponse.LoginResponse;
import com.crater.craterlogin.exception.RequestFormatException;
import com.crater.craterlogin.service.ValidateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ValidateController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ValidateService validateService;

    @PostMapping("/validateController/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            validateRequest(loginRequest);
            var loginDto = generateLoginDto(loginRequest);
            var loginResultDto = validateService.login(loginDto);
            return new LoginResponse(Status.SUCCESS, loginResultDto.token());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }

    private void validateRequest(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new RequestFormatException("login request is null");
        }
        var errorMessage = new ArrayList<String>();
        if (StringUtils.isBlank(loginRequest.username())) {
            errorMessage.add("username is null");
        }
        if (StringUtils.isBlank(loginRequest.password())) {
            errorMessage.add("password is null");
        }
        if (!errorMessage.isEmpty()) {
            throw new RequestFormatException(errorMessage.toString());
        }
    }

    private LoginDto generateLoginDto(LoginRequest loginRequest) {
        return new LoginDto(loginRequest.username(), loginRequest.password());
    }

    @Autowired
    public void setValidateService(ValidateService validateService) {
        this.validateService = validateService;
    }
}
