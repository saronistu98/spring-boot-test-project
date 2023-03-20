package com.saron.spring.test.user.service;

import com.saron.spring.test.user.dao.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void save(User user);

    User findByEmail(String userEmail);

    boolean exists(String userEmail);

}
