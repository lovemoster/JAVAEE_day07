package com.powernode.service.impl;

import com.powernode.dao.UserDao;
import com.powernode.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String username, String password) {
        UserDao userDao = new UserDao();
        return userDao.login(username, password);
    }
}
