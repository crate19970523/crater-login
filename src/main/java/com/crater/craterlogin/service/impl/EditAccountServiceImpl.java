package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.editAccountService.AddAccountDto;
import com.crater.craterlogin.bean.entity.db.UserData;
import com.crater.craterlogin.bean.entity.db.UserKyc;
import com.crater.craterlogin.dao.db.UserDataDao;
import com.crater.craterlogin.dao.db.UserKycDao;
import com.crater.craterlogin.exception.AccountException;
import com.crater.craterlogin.service.EditAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EditAccountServiceImpl implements EditAccountService {
    private UserDataDao userDataDao;
    private UserKycDao userKycDao;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void addAccount(AddAccountDto addAccountDto) {
        var isHaveSameUserName = queryIsHaveSameUserName(addAccountDto.userName());
        if (isHaveSameUserName) {
            throw new AccountException("found same user name");
        }
        var encodedPassword = generateEncodePassword(addAccountDto.password());
        var userData = new UserData(addAccountDto.userName(), encodedPassword, true, LocalDateTime.now(), "system", null, null);
        var userKyc = new UserKyc(addAccountDto.userName(), addAccountDto.email(), LocalDateTime.now(), "system", null, null);
        insertUserData(userData);
        insertUserKyc(userKyc);
    }

    private boolean queryIsHaveSameUserName(String userName) {
        try {
            return !userDataDao.select(new UserData().setUserName(userName)).isEmpty();
        } catch (Exception e) {
            throw new AccountException("query user data failed", e);
        }
    }

    private String generateEncodePassword(String password) {
        try {
            return passwordEncoder.encode(password);
        } catch (Exception e) {
            throw new AccountException("encode password failed", e);
        }
    }

    private void insertUserData(UserData userData) {
        try {
            userDataDao.insert(userData);
        } catch (Exception e) {
            throw new AccountException("insert user data failed", e);
        }
    }

    private void insertUserKyc(UserKyc userKyc) {
        try {
            userKycDao.insert(userKyc);
        } catch (Exception e) {
            throw new AccountException("insert user kyc failed", e);
        }
    }

    @Autowired
    public void setUserDataDao(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }

    @Autowired
    public void setUserKycDao(UserKycDao userKycDao) {
        this.userKycDao = userKycDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
