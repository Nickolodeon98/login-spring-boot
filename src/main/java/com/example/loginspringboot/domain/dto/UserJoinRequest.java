package com.example.loginspringboot.domain.dto;

import com.example.loginspringboot.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;
    private String emailAddress;
    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .phoneNumber(this.phoneNumber)
                .emailAddress(this.emailAddress)
                .build();
    }
}
