package com.lgcns.inspire3_blog.board.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BOARD")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;

    @Column(nullable = false)
    private Integer userId;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String content;

    @Column(length = 50)
    private String category; // 카테고리

    @Column(length = 200)
    private String url; // 관련 링크

    @Column(length = 100)
    private String hashtag; // 해시태그

    private String createdAt;
    private String updatedAt;
    private Integer viewCount;
    private Integer likeCount;
}