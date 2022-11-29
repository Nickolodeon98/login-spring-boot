package com.example.loginspringboot.controller;

import com.example.loginspringboot.domain.dto.UserDto;
import com.example.loginspringboot.domain.dto.UserJoinRequest;
import com.example.loginspringboot.exception.ErrorCode;
import com.example.loginspringboot.exception.HospitalReviewAppException;
import com.example.loginspringboot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    UserJoinRequest userJoinRequest;
    @BeforeEach
    void setUp() {
        userJoinRequest = UserJoinRequest.builder()
                .userName("sjeon0730")
                .password("abcdefgh")
                .emailAddress("sjeon0730@gmail.com")
                .phoneNumber("010-4242-6416")
                .build();
    }

    @DisplayName("회원가입을 성공한다.")
    @Test
    @WithMockUser
    void login_success() throws Exception {
        UserDto userDto = UserDto.builder()
                .userName("sjeon0730")
                .email("sjeon0730@gmail.com")
                .phoneNumber("010-4242-6416")
                .build();

        given(userService.logIn(any())).willReturn(userDto);

        String url = "/login/login";

        mockMvc.perform(post(url).with(csrf())
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
    @WithMockUser
    void login_fail() throws Exception {
        given(userService.logIn(any()))
                .willThrow(new HospitalReviewAppException(ErrorCode.DUPLICATE_USER_NAME, ErrorCode.DUPLICATE_USER_NAME.getMessage()));

        String url = "/login/login";

        mockMvc.perform(post(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andExpect(status().isConflict());

        verify(userService).logIn(userJoinRequest);
    }

    @Test
    @DisplayName("로그인 실패 - id 없음")
    @WithMockUser
    void unauthorized_id() throws Exception {
        // 무엇을 보내서
        // 무엇을 받을까? : NOT_FOUND
        given(userService.authorize(any(), any())).willThrow(new HospitalReviewAppException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage()));

        String url = "/login/login";

        mockMvc.perform(post(url).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).contentType(objectMapper.writeValueAsString(userJoinRequest)))
                .andExpect(status().isNotFound());

        verify(userService).authorize(any(), any());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    @WithMockUser
    void unauthorized_password() throws Exception {

    }
}