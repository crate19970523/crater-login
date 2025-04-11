package com.crater.craterlogin.dao.db;

import com.crater.craterlogin.bean.entity.db.InvitationCode;

import java.util.List;

public interface InvitationCodeDao {
    void insert(InvitationCode invitationCode);
    void update(InvitationCode invitationCode);
    List<InvitationCode> select(InvitationCode invitationCode);
    void delete(InvitationCode invitationCode);
}
