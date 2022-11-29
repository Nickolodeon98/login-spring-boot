package com.example.loginspringboot.controller;

import com.example.loginspringboot.domain.dto.*;
import com.example.loginspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> joinUsers(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.join(userJoinRequest);
        return Response.success(new UserJoinResponse(userDto.getUserName(), userDto.getEmail(), userDto.getPhoneNumber()));
    }

    @PostMapping("/login")
    public Response<UserJoinResponse> logInUsers(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.logIn(userJoinRequest);
        return Response.success(new UserJoinResponse(userDto.getUserName(), userDto.getEmail(), userDto.getPhoneNumber()));
    }

    @PostMapping("/authentication")
    public Response<UserLoginResponse> authenticateUsers(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.authenticate(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
