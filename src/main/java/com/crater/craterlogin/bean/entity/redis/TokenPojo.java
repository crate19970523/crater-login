package com.crater.craterlogin.bean.entity.redis;

public record TokenPojo(String token, String userId, long timeout) {
}
