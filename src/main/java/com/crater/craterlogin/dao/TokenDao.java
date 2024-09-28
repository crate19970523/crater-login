package com.crater.craterlogin.dao;

import com.crater.craterlogin.bean.entity.redis.TokenPojo;

public interface TokenDao {
    TokenPojo getByToken(String token);
    void update(TokenPojo tokenPojo);
    boolean exists(String token);
    void deleteTokenByToken(String token);
}
