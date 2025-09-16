package com.lgcns.inspire3_blog.userrank.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_rank")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserRankEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;          // users.user_id

    @Column(name = "post_count", nullable = false)
    private int postCount;

    @Column(name = "comment_count", nullable = false)
    private int commentCount;

    @Column(name = "score", nullable = false)
    private long score;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}