package com.example.loginspringboot.service;

import com.example.loginspringboot.domain.dto.UserDto;
import com.example.loginspringboot.domain.dto.UserJoinRequest;
import com.example.loginspringboot.domain.entity.User;
import com.example.loginspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest userJoinRequest) {
        // 비즈니스 로직 : 회원가입

        // userName(회원 id) 중복 체크, 중복일 시 예외 처리 (사용자 실수는 예외, 런타임 에러는 에러)

        // 회원 가입 .save()
        return UserDto.builder().build();
    }

    public UserDto logIn(UserJoinRequest userJoinRequest) {
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {throw new RuntimeException("아이디 중복");});
        User savedUser = userRepository.save(userJoinRequest.toEntity());
        return UserDto.builder()
                .userName(savedUser.getUserName())
                .email(savedUser.getEmailAddress())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }
}
