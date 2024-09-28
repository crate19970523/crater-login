package com.crater.craterlogin.dao.db;

import com.crater.craterlogin.bean.entity.db.UserData;

import java.util.List;

public interface UserDataDao {
    void insert(UserData userDataPojo);
    void update(UserData userDataPojo);
    List<UserData> select(UserData userDataPojo);
    void delete(UserData userDataPojo);
}
