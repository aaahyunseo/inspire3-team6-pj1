package com.lgcns.inspire3_blog.user.service;

import com.lgcns.inspire3_blog.user.domain.dto.LoginRequestDTO;
import com.lgcns.inspire3_blog.user.domain.dto.LoginResponseDTO;
import com.lgcns.inspire3_blog.user.domain.dto.UserRequestDTO;
import com.lgcns.inspire3_blog.user.domain.dto.UserResponseDTO;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;
import com.lgcns.inspire3_blog.user.repository.RefreshTokenRepository;
import com.lgcns.inspire3_blog.user.repository.UserRepository;
import com.lgcns.inspire3_blog.user.repository.RefreshTokenRepository;
import com.lgcns.inspire3_blog.util.JwtProvider;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;  // BCrypt

    @Autowired
    private final JwtProvider jwtprovider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository ;
    public void logout(String email) {
        System.out.println(">>> service logout redis delete"); 
        // Redis에서 Refresh Token 제거
        refreshTokenRepository.delete(email); 
    }

    // 회원가입
    public UserResponseDTO signup(UserRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .passwd(passwordEncoder.encode(request.getPasswd())) // 암호화
                .name(request.getName())
                .birthday(request.getBirthday())
                .build();

        userRepository.save(user);

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();
    }

    // 로그인
    public LoginResponseDTO signin(LoginRequestDTO request) {
        System.out.println(">>> service signin");

        UserEntity entity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPasswd(), entity.getPasswd())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 생성
        String accToken = jwtprovider.generateAccessToken(request.getEmail());
        String refToken = jwtprovider.generateRefreshToken(request.getEmail());

        return LoginResponseDTO.builder()
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .name(entity.getName())
                .birthday(entity.getBirthday())
                .accessToken(accToken)
                .refreshToken(refToken)
                .build();
    }

}