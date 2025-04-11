package com.crater.craterlogin.bean.entity.db;

import java.time.LocalDateTime;

public class UserKyc {
    private String userName;
    private String email;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;

    public UserKyc() {
    }

    public UserKyc(String userName, String email, LocalDateTime createTime, String createUser, LocalDateTime updateTime, String updateUser) {
        this.userName = userName;
        this.email = email;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    public String userName() {
        return userName;
    }

    public UserKyc setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String email() {
        return email;
    }

    public UserKyc setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime createTime() {
        return createTime;
    }

    public UserKyc setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String createUser() {
        return createUser;
    }

    public UserKyc setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public LocalDateTime updateTime() {
        return updateTime;
    }

    public UserKyc setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String updateUser() {
        return updateUser;
    }

    public UserKyc setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }
}
