package com.example.loginspringboot.domain.dto;

import com.example.loginspringboot.domain.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class UserJoinRequest {
    private String userName;
    private String password;
    private String emailAddress;
    private String phoneNumber;

    public User toEntity(String password) {
        return User.builder()
                .userName(this.userName)
                .password(password)
                .phoneNumber(this.phoneNumber)
                .emailAddress(this.emailAddress)
                .build();
    }
}
