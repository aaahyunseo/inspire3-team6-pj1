package com.lgcns.inspire3_blog.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);    // 로그인/중복체크
    boolean existsByEmail(String email);               // 회원가입 중복검증
    Optional<UserEntity> findById(Long userId);        // PK로 조회
}