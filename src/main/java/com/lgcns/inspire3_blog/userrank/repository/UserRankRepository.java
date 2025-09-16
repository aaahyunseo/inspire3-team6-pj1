package com.lgcns.inspire3_blog.userrank.repository;

import com.lgcns.inspire3_blog.userrank.domain.entity.UserRankEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRankRepository extends JpaRepository<UserRankEntity, Long> {

    @Modifying
    @Query("UPDATE UserRankEntity r " +
           "SET r.postCount = r.postCount + :delta, r.score = r.score + :scoreDelta, r.updatedAt = CURRENT_TIMESTAMP " +
           "WHERE r.userId = :userId")
    int increasePostAndScore(long userId, int delta, long scoreDelta);

    @Modifying
    @Query("UPDATE UserRankEntity r " +
           "SET r.commentCount = r.commentCount + :delta, r.score = r.score + :scoreDelta, r.updatedAt = CURRENT_TIMESTAMP " +
           "WHERE r.userId = :userId")
    int increaseCommentAndScore(long userId, int delta, long scoreDelta);

    List<UserRankEntity> findAllByOrderByScoreDesc(Pageable pageable);
}