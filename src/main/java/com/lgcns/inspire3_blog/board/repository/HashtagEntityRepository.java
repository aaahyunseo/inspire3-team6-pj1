package com.lgcns.inspire3_blog.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.board.domain.entity.HashtagEntity;

public interface HashtagEntityRepository extends JpaRepository<HashtagEntity,Integer> {
    Optional<HashtagEntity> findByName(String name);
}
