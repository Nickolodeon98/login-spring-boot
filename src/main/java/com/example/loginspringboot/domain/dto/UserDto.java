package com.example.loginspringboot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private String userName;
    private String email;
    private String phoneNumber;
}
