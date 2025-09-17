package com.lgcns.inspire3_blog.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // 제목으로 게시글 검색
    List<BoardEntity> findByTitleContaining(String keyword);

    // 최신순 조회
    List<BoardEntity> findAllByOrderByCreatedAtDesc();

    // 유저가 작성한 게시글 목록 조회
    List<BoardEntity> findAllByUser(UserEntity user);

    // 카테고리 별 게시글 목록 조회
    List<BoardEntity> findByCategory(String category);
}