package com.lgcns.inspire3_blog.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;


import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // 카테고리 이름으로 게시글 조회 (다대다 관계)
    @Query("SELECT b FROM BoardEntity b " +
       "JOIN b.boardCategories bc " +
       "JOIN bc.category c " +
       "WHERE c.name = :categoryName")
    List<BoardEntity> findByCategoryName(@Param("categoryName") String categoryName);


    // 해시태그 이름으로 게시글 조회 (다대다 관계)
    @Query("SELECT b FROM BoardEntity b " +
       "JOIN b.boardHashtags bh " +
       "JOIN bh.hashtag h " +
       "WHERE h.name = :hashtagName")
    List<BoardEntity> findByHashtagName(@Param("hashtagName") String hashtagName);


    // 제목으로 게시글 검색
    List<BoardEntity> findByTitleContaining(String keyword);

    // 작성자(userId)로 게시글 조회
    @Query("SELECT b FROM BoardEntity b WHERE b.userId = :userId")
    List<BoardEntity> findByUserId(@Param("userId") Integer userId);

    // 최신순 조회
    List<BoardEntity> findAllByOrderByCreatedAtDesc();
}