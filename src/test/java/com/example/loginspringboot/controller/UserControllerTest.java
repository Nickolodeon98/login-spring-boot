package com.example.loginspringboot.controller;

import com.example.loginspringboot.domain.dto.UserDto;
import com.example.loginspringboot.domain.dto.UserJoinRequest;
import com.example.loginspringboot.exception.ErrorCode;
import com.example.loginspringboot.exception.HospitalReviewAppException;
import com.example.loginspringboot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @DisplayName("회원가입을 성공한다.")
    @Test
    void login_success() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("sjeon0730")
                .password("abcdefgh")
                .emailAddress("sjeon0730@gmail.com")
                .phoneNumber("010-4242-6416")
                .build();

        UserDto userDto = UserDto.builder()
                .userName("sjeon0730")
                .email("sjeon0730@gmail.com")
                .phoneNumber("010-4242-6416")
                .build();

        given(userService.logIn(any())).willReturn(userDto);

        String url = "/login/login";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result.userName").value("sjeon0730"))
                .andExpect(jsonPath("$.result.email").value("sjeon0730@gmail.com"))
                .andExpect(jsonPath("$.result.phoneNumber").value("010-4242-6416"));

        verify(userService).logIn(userJoinRequest);
    }
    @DisplayName("회원가입을 실패한다.")
    @Test
    void login_fail() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("sjeon0730")
                .password("abcdefgh")
                .emailAddress("sjeon0730@gmail.com")
                .phoneNumber("010-4242-6416")
                .build();

        UserDto userDto = UserDto.builder()
                .userName("sjeon0730")
                .email("sjeon0730@gmail.com")
                .phoneNumber("010-4242-6416")
                .build();

        given(userService.logIn(any()))
                .willThrow(new HospitalReviewAppException(ErrorCode.DUPLICATE_USER_NAME, ErrorCode.DUPLICATE_USER_NAME.getMessage()));

        String url = "/login/login";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andExpect(status().isConflict());

        verify(userService).logIn(userJoinRequest);
    }
}