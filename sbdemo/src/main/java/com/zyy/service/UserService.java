package com.zyy.service;

import com.zyy.model.MyResult;
import com.zyy.model.User;

public interface UserService {
    MyResult login(User user);
}
