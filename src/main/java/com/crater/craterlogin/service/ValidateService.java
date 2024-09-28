package com.crater.craterlogin.service;

import com.crater.craterlogin.bean.dto.validateService.LoginDto;
import com.crater.craterlogin.bean.dto.validateService.LoginResultDto;

public interface ValidateService {
    LoginResultDto login(LoginDto loginDto);
}
