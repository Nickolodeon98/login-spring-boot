package com.example.loginspringboot.service;

import com.example.loginspringboot.domain.dto.UserDto;
import com.example.loginspringboot.domain.dto.UserJoinRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserDto join(UserJoinRequest userJoinRequest) {
        return new UserDto(userJoinRequest.getUserName(), userJoinRequest.getEmailAddress());
    }
}
