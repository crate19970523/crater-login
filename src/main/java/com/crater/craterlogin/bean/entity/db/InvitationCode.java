package com.crater.craterlogin.bean.entity.db;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvitationCode {
    private String code;
    private String codeDescription;
    private Boolean isActive;
    private LocalDate expirationDate;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;

    public InvitationCode() {
    }

    public InvitationCode(String code, String codeDescription, Boolean isActive, LocalDate expirationDate,
                          LocalDateTime createTime, String createUser, LocalDateTime updateTime, String updateUser) {
        this.code = code;
        this.codeDescription = codeDescription;
        this.isActive = isActive;
        this.expirationDate = expirationDate;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    public String code() {
        return code;
    }

    public InvitationCode setCode(String code) {
        this.code = code;
        return this;
    }

    public String codeDescription() {
        return codeDescription;
    }

    public InvitationCode setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public InvitationCode setActive(boolean active) {
        isActive = active;
        return this;
    }

    public LocalDate expirationDate() {
        return expirationDate;
    }

    public InvitationCode setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public LocalDateTime createTime() {
        return createTime;
    }

    public InvitationCode setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String createUser() {
        return createUser;
    }

    public InvitationCode setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public LocalDateTime updateTime() {
        return updateTime;
    }

    public InvitationCode setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String updateUser() {
        return updateUser;
    }

    public InvitationCode setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }
}