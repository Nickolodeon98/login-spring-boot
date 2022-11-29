package com.example.loginspringboot.service;

import com.example.loginspringboot.domain.dto.UserDto;
import com.example.loginspringboot.domain.dto.UserJoinRequest;
import com.example.loginspringboot.domain.entity.User;
import com.example.loginspringboot.exception.ErrorCode;
import com.example.loginspringboot.exception.HospitalReviewAppException;
import com.example.loginspringboot.repository.UserRepository;
import com.example.loginspringboot.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder; // 패스워드 암호화 도구
    @Value("${jwt.token.secret}")
    private String secretKey;
    private final long expiryTimeMs = 1000 * 60 * 60;

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

    public String authenticate(String userName, String password) {
        //TODO: userName 이 존재하는지 확인한다.
        User foundUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage()));

        //TODO: password 가 올바른지 확인한다.
        if (!encoder.matches(password, foundUser.getPassword()))
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());

        //TODO: JWT 토큰을 발급한다. 토큰은 인증, 인가를 거친 번호표와 같다. 있으면 통과된다.
        return JwtUtil.createToken(userName, secretKey, expiryTimeMs);
    }
}
