package com.example.loginspringboot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinResponse {

    private String userName;
    private String email;
    private String phoneNumber;
}
