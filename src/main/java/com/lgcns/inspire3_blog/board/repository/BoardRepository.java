package com.lgcns.inspire3_blog.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;


import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    List<BoardEntity> findByCategory(String category); // 카테고리별 조회
}