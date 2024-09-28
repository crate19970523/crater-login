package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.userDetailsServiceImpl.UserDetailsServiceDto;
import com.crater.craterlogin.bean.entity.db.UserData;
import com.crater.craterlogin.dao.db.UserDataDao;
import com.crater.craterlogin.exception.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDataDao userDataDao;

    @Override
    public UserDetailsServiceDto loadUserByUsername(String username) throws UsernameNotFoundException {
        var userData = callDbSelectUserData(username);
        return userData.orElse(null);
    }

    private Optional<UserDetailsServiceDto> callDbSelectUserData(String username) {
        try {
            var userData = userDataDao.select(new UserData().setUserName(username)).getFirst();
            return Optional.of(new UserDetailsServiceDto(userData.userName(), userData.password(),
                    List.of(new SimpleGrantedAuthority("admin"))));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DbException("Error selecting user data from database", e);
        }
    }

    @Autowired
    public void setUserDataDao(UserDataDao userDataDao) {
        this.userDataDao = userDataDao;
    }
}
