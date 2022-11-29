package com.example.loginspringboot.service;

import com.example.loginspringboot.domain.dto.UserDto;
import com.example.loginspringboot.domain.dto.UserJoinRequest;
import com.example.loginspringboot.domain.entity.User;
import com.example.loginspringboot.exception.ErrorCode;
import com.example.loginspringboot.exception.HospitalReviewAppException;
import com.example.loginspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder; // 패스워드 암호화 도구

    public UserDto join(UserJoinRequest userJoinRequest) {
        // 비즈니스 로직 : 회원가입

        // userName(회원 id) 중복 체크, 중복일 시 예외 처리 (사용자 실수는 예외, 런타임 에러는 에러)

        // 회원 가입 .save()
        return UserDto.builder().build();
    }

    public UserDto logIn(UserJoinRequest userJoinRequest) {
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user ->
                {throw new HospitalReviewAppException(ErrorCode.DUPLICATE_USER_NAME, ErrorCode.DUPLICATE_USER_NAME.getMessage());});
        User savedUser = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));
        return UserDto.builder()
                .userName(savedUser.getUserName())
                .email(savedUser.getEmailAddress())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    public String authenticate(String id, String password) {
        // 일단 틀만 짜놓았다.
        //TODO: 주어진 아이디와 비밀번호로 토큰을 발급받을 수 있는지 여부를 검사한다.

        return "";
    }
}
