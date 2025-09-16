package com.lgcns.inspire3_blog.board.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.board.domain.entity.BoardCategory;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, UUID> {
    
}
