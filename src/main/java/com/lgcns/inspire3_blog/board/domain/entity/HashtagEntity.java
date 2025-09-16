package com.lgcns.inspire3_blog.board.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HASHTAG")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HashtagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hashtagId;

    @Column(length = 100, nullable = false)
    private String name;
}