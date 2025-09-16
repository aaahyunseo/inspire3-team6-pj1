package com.lgcns.inspire3_blog.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.board.domain.entity.CategoryEntity;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);
}
