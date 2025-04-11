package com.crater.craterlogin.controller;

import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeDto;
import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeResultDto;
import com.crater.craterlogin.bean.response.Status;
import com.crater.craterlogin.bean.response.invitationController.GetInvitationCodeResponse;
import com.crater.craterlogin.exception.RequestFormatException;
import com.crater.craterlogin.service.InvitationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "邀請碼控制器", description = "處理邀請碼相關操作的控制器")
public class InvitationCodeController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private InvitationCodeService invitationCodeService;

    @GetMapping("/getInvitationCode")
    @Operation(summary = "獲取邀請碼資訊", description = "根據提供的邀請碼查詢其存在性、活躍狀態和過期狀態")
    public GetInvitationCodeResponse getInvitationCode(@Parameter(description = "要查詢的邀請碼") @PathParam("code") String code) {
        try {
            validateCode(code);
            var resultDto = invitationCodeService.getInvitationCode(new GetInvitationCodeDto(code));
            return generateResopnse(resultDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private void validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new RequestFormatException("code is null or blank");
        }
    }

    private GetInvitationCodeResponse generateResopnse(GetInvitationCodeResultDto resultDto) {
        return new GetInvitationCodeResponse(Status.SUCCESS, resultDto.isExist(), resultDto.isActive(),
                resultDto.isExpired());
    }

    @Autowired
    public void setInvitationCodeService(InvitationCodeService invitationCodeService) {
        this.invitationCodeService = invitationCodeService;
    }
}
