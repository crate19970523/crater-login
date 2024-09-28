package com.crater.craterlogin.bean.entity.db;

import java.time.LocalDateTime;

public class UserData {
    private String userName;
    private String password;
    private boolean isActive;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;

    public UserData() {

    }

    public UserData(String userName, String password, boolean isActive, LocalDateTime createTime, String crateUser,
                    LocalDateTime updateTime, String updateUser) {
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.createTime = createTime;
        this.createUser = crateUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    public String userName() {
        return userName;
    }

    public UserData setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String password() {
        return password;
    }

    public UserData setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserData setActive(boolean active) {
        isActive = active;
        return this;
    }

    public LocalDateTime createTime() {
        return createTime;
    }

    public UserData setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public String createUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime updateTime() {
        return updateTime;
    }

    public UserData setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String updateUser() {
        return updateUser;
    }

    public UserData setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }
}
