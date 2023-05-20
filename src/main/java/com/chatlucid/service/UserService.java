package com.chatlucid.service;

import com.chatlucid.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    User saveUser(User user);
}
