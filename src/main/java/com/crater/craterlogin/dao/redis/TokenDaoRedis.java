package com.crater.craterlogin.dao.redis;

import com.crater.craterlogin.bean.entity.redis.TokenPojo;
import com.crater.craterlogin.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenDaoRedis implements TokenDao {
    private final static String LAST_TOKEN_LABEL = "_lastToken";
    private RedisTemplate<String, TokenPojo> tokenRedisTemplate;


    @Override
    public TokenPojo getByToken(String token) {
        return tokenRedisTemplate.opsForValue().get(token);
    }

    @Override
    public void update(TokenPojo tokenPojo) {
        var oldTokenInfo = tokenRedisTemplate.opsForValue().get(tokenPojo.userId() + LAST_TOKEN_LABEL);
        if (oldTokenInfo != null) {
            tokenRedisTemplate.delete(oldTokenInfo.token());
        }
        var valueOps = tokenRedisTemplate.opsForValue();
        valueOps.set(tokenPojo.token(), tokenPojo, tokenPojo.timeout(), TimeUnit.MINUTES);
        valueOps.set(tokenPojo.userId() + LAST_TOKEN_LABEL, tokenPojo);
    }

    @Override
    public boolean exists(String token) {
        var valueOps = tokenRedisTemplate.opsForValue();
        return valueOps.get(token) != null;
    }

    @Override
    public void deleteTokenByToken(String token) {
        tokenRedisTemplate.delete(token);
    }

    @Autowired
    public void setTokenRedisTemplate(RedisTemplate<String, TokenPojo> tokenRedisTemplate) {
        this.tokenRedisTemplate = tokenRedisTemplate;
    }
}
