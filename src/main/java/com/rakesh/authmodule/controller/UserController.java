package com.rakesh.authmodule.controller;

import com.rakesh.authmodule.dtos.*;
import com.rakesh.authmodule.exception.InvalidUserException;
import com.rakesh.authmodule.models.Token;
import com.rakesh.authmodule.models.User;
import com.rakesh.authmodule.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return UserDto.from(user);
    }

    @PostMapping("/login1")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidUserException {
        Token token =  userService.login(loginRequestDto.getEmail(),  loginRequestDto.getPassword());
        return new LoginResponseDto(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        ResponseEntity<Void> responseEntity;
        try {
            userService.logout(logoutRequestDto.getToken());
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("logoutRequestDto = " + e.getMessage());
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/validateToken/{tokenValue}")
    public UserDto validateToken(@PathVariable String tokenValue) {
        return userService.validateToken(tokenValue);
    }
}
