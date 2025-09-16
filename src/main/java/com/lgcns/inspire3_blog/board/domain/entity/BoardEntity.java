package com.lgcns.inspire3_blog.board.domain.entity;


import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BOARD")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(length = 200)
    private String url; // 관련 링크

    private String createdAt;
    private String updatedAt;
    private Integer viewCount;
    private Integer likeCount;

    // 카테고리, 해시태크
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardCategory> boardCategories;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardHashtag> boardHashtags;

}