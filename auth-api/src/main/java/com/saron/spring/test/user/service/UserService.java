package com.saron.spring.test.user.service;

import com.saron.spring.test.user.dao.UserEntity;

public interface UserService {

    void save(UserEntity userEntity);

    UserEntity findByEmail(String userEmail);

    boolean exists(String userEmail);

}
