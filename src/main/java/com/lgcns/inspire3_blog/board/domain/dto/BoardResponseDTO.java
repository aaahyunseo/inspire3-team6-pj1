package com.lgcns.inspire3_blog.board.domain.dto;

import java.util.List;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;

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
    private List<String> categories;
    private String url;
    private List<String> hashtags;
    private String createdAt;
    private Integer viewCount;
    private Integer likeCount;

    public static BoardResponseDTO from(BoardEntity entity) {
        return BoardResponseDTO.builder()
                .boardId(entity.getBoardId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .categories(
                    entity.getBoardCategories() == null ? null :
                entity.getBoardCategories().stream()
                        .map(boardCategory -> boardCategory.getCategory().getName())
                        .toList()
                )
                .url(entity.getUrl())
                .hashtags(                
                    entity.getBoardHashtags() == null ? null :
                    entity.getBoardHashtags().stream()
                        .map(boardHashtag -> boardHashtag.getHashtag().getName())
                        .toList()
                )
                .createdAt(entity.getCreatedAt())
                .viewCount(entity.getViewCount())
                .likeCount(entity.getLikeCount())
                .build();
    }
}