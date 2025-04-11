package com.crater.craterlogin.bean.request.editAccountController;

import jakarta.validation.constraints.NotEmpty;

public record AddAccountRequest(@NotEmpty String userName, @NotEmpty String password, String email) {
}
