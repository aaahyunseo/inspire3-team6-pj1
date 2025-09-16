package com.lgcns.inspire3_blog.user.ctrl;

import com.lgcns.inspire3_blog.user.domain.dto.LoginRequestDTO;
import com.lgcns.inspire3_blog.user.domain.dto.LoginResponseDTO;
import com.lgcns.inspire3_blog.user.domain.dto.UserRequestDTO;
import com.lgcns.inspire3_blog.user.domain.dto.UserResponseDTO;
import com.lgcns.inspire3_blog.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signin(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.signin(request));
    }
}