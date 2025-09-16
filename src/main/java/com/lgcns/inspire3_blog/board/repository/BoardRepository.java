package com.lgcns.inspire3_blog.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;


import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // 제목으로 게시글 검색
    List<BoardEntity> findByTitleContaining(String keyword);

    // 작성자(userId)로 게시글 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.user.userId = :userId")
    List<BoardEntity> findByUserId(@Param("userId") Long userId);


    // 최신순 조회
    List<BoardEntity> findAllByOrderByCreatedAtDesc();
}