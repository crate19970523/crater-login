package com.crater.craterlogin.service;

import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeDto;
import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeResultDto;

public interface InvitationCodeService {
    GetInvitationCodeResultDto getInvitationCode(GetInvitationCodeDto getInvitationCodeDto);
}
