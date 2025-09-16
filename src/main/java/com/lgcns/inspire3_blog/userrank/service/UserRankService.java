package com.lgcns.inspire3_blog.userrank.service;

import com.lgcns.inspire3_blog.userrank.domain.entity.UserRankEntity;
import com.lgcns.inspire3_blog.userrank.repository.UserRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRankService {
    private final UserRankRepository repo;

    private static final int POST_WEIGHT = 10;
    private static final int COMMENT_WEIGHT = 3;

    @Transactional
    public void increasePostCount(long userId) {
        long scoreDelta = POST_WEIGHT;
        int updated = repo.increasePostAndScore(userId, 1, scoreDelta);
        if (updated == 0) {
            repo.save(UserRankEntity.builder()
                    .userId(userId)
                    .postCount(1)
                    .commentCount(0)
                    .score(scoreDelta)
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
    }

    @Transactional
    public void increaseCommentCount(long userId) {
        long scoreDelta = COMMENT_WEIGHT;
        int updated = repo.increaseCommentAndScore(userId, 1, scoreDelta);
        if (updated == 0) {
            repo.save(UserRankEntity.builder()
                    .userId(userId)
                    .postCount(0)
                    .commentCount(1)
                    .score(scoreDelta)
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
    }

    @Transactional(readOnly = true)
    public List<UserRankEntity> getTopN(int n) {
        return repo.findAllByOrderByScoreDesc(PageRequest.of(0, n));
    }
}