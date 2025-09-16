package com.lgcns.inspire3_blog.board.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardRequestDTO {
    private Long userId;
    private String title;
    private String content;
    private String category;
    private String url;
    private List<String> hashtags;   // 해시태그 이름 리스트
}
