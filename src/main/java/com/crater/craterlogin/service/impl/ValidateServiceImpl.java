package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.validateService.LoginDto;
import com.crater.craterlogin.bean.dto.validateService.LoginResultDto;
import com.crater.craterlogin.bean.entity.db.UserData;
import com.crater.craterlogin.bean.entity.redis.TokenPojo;
import com.crater.craterlogin.dao.TokenDao;
import com.crater.craterlogin.dao.db.UserDataDao;
import com.crater.craterlogin.exception.AccountException;
import com.crater.craterlogin.exception.DbException;
import com.crater.craterlogin.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ValidateServiceImpl implements ValidateService {
    private PasswordEncoder passwordEncoder;
    private UserDataDao userDataDao;
    private TokenDao tokenDao;
    private Long loginTokenTimeout;

    @Override
    public LoginResultDto login(LoginDto loginDto) {
        var userQueryResult = queryUserData(loginDto.username());
        if (!passwordEncoder.matches(loginDto.password(), userQueryResult
                .orElseThrow(() -> new AccountException("account not found")).password())) {
            throw new AccountException("Invalid password");
        }
        var token = generateNotRepeatedToken();
        insertTokenToRedis(token, loginDto);
        return new LoginResultDto(token);
    }

    private Optional<UserData> queryUserData(String username) {
        try {
            Optional<UserData> result;
            var userQueryResult = userDataDao.select(new UserData().setUserName(username).setActive(true));
            if (userQueryResult.isEmpty()) {
                result = Optional.empty();
            } else {
                result = Optional.of(userQueryResult.getFirst());
            }
            return result;
        } catch (Exception e) {
            throw new DbException("query user data failed", e);
        }
    }

    private String generateNotRepeatedToken() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (tokenDao.exists(token));
        return token;
    }

    private void insertTokenToRedis(String token, LoginDto loginDto) {
        try {
            tokenDao.update(new TokenPojo(token, loginDto.username(), loginTokenTimeout));
        } catch (Exception e) {
            throw new DbException("insert token to redis failed", e);
        }
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDataDao(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }

    @Autowired
    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Value("${login.tokenTimeout}")
    public void setLoginTokenTimeout(Long loginTokenTimeout) {
        this.loginTokenTimeout = loginTokenTimeout;
    }
}
