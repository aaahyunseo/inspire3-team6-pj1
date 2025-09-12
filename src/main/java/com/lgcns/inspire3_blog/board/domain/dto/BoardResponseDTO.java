package com.lgcns.inspire3_blog.board.domain.dto;

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
public class BoardResponseDTO {
    private Integer boardId;
    private Integer userId;
    private String title;
    private String content;
    private String category;
    private String url;
    private String hashtag;
    private String createdAt;
    private Integer viewCount;
    private Integer likeCount;
}