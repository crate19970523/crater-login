package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeDto;
import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeResultDto;
import com.crater.craterlogin.bean.entity.db.InvitationCode;
import com.crater.craterlogin.dao.db.InvitationCodeDao;
import com.crater.craterlogin.exception.DbException;
import com.crater.craterlogin.service.InvitationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InvitationCodeServiceImpl implements InvitationCodeService {
    private InvitationCodeDao invitationCodeDao;

    @Override
    public GetInvitationCodeResultDto getInvitationCode(GetInvitationCodeDto getInvitationCodeDto) {
        var invitationCode = selectInvitationCode(getInvitationCodeDto.code());
        return generateGetInvitationCodeResultDto(invitationCode);
    }

    private Optional<InvitationCode> selectInvitationCode(String code) {
        try {
            var queryParam = new InvitationCode().setCode(code);
            var queryResult = invitationCodeDao.select(queryParam);
            return queryResult.isEmpty() ? Optional.empty() : Optional.of(queryResult.getFirst());
        } catch (Exception e) {
            throw new DbException("query invitation code failed", e);
        }
    }

    private GetInvitationCodeResultDto generateGetInvitationCodeResultDto(Optional<InvitationCode> invitationCode) {
        try {
            GetInvitationCodeResultDto result;
            if (invitationCode.isEmpty()) {
                result = new GetInvitationCodeResultDto(false, null, null);
            } else {
                var code = invitationCode.get();
                var isExpired = code.expirationDate() != null && code.expirationDate().isBefore(LocalDate.now());
                result = new GetInvitationCodeResultDto(true, code.isActive(), isExpired);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("generate get invitation code result dto failed", e);
        }
    }

    @Autowired
    public void setInvitationCodeDao(InvitationCodeDao invitationCodeDao) {
        this.invitationCodeDao = invitationCodeDao;
    }
}
