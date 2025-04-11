package com.crater.craterlogin.bean.response.invitationController;

import com.crater.craterlogin.bean.response.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "獲取邀請碼的響應對象")
public record GetInvitationCodeResponse(
        @Schema(description = "請求處理狀態", example = "SUCCESS") Status status,
        @Schema(description = "邀請碼是否存在", example = "true") boolean isExist,
        @Schema(description = "邀請碼是否處於活躍狀態", example = "true") Boolean isActivated,
        @Schema(description = "邀請碼是否已過期", example = "false") Boolean isExpired) {
}
