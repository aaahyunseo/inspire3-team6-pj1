package com.lgcns.inspire3_blog.userrank.service;

import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;
import com.lgcns.inspire3_blog.user.repository.UserRepository;
import com.lgcns.inspire3_blog.userrank.domain.dto.UserRankDTO;
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
    private final UserRepository userRepo; 

    private static final int POST_WEIGHT = 10;
    private static final int COMMENT_WEIGHT = 3;

    @Transactional
    public void increasePostCount(long userId) {
        long scoreDelta = POST_WEIGHT;
        int updated = repo.increasePostAndScore(userId, 1, scoreDelta);

        if (updated == 0) {
            UserEntity user = userRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            repo.save(UserRankEntity.builder()
                    .user(user)
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
            UserEntity user = userRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            repo.save(UserRankEntity.builder()
                    .user(user)
                    .postCount(0)
                    .commentCount(1)
                    .score(scoreDelta)
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
    }

    @Transactional(readOnly = true)
    public List<UserRankDTO> getTopN(int n) {
        return repo.findAllByOrderByScoreDesc(PageRequest.of(0, n))
                .stream()
                .map(r -> UserRankDTO.builder()
                        .userId(r.getUser().getUserId())
                        .userName(r.getUser().getName())
                        .postCount(r.getPostCount())
                        .commentCount(r.getCommentCount())
                        .score(r.getScore())
                        .build()
                ).toList();
    }
}