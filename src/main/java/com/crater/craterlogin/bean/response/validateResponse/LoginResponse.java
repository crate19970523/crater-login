package com.crater.craterlogin.bean.response.validateResponse;

import com.crater.craterlogin.bean.response.Status;

public record LoginResponse(Status status, String token) {
}
