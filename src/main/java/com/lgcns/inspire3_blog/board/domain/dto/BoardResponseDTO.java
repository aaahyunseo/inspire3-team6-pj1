package com.lgcns.inspire3_blog.board.domain.dto;

import java.util.List;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire3_blog.board.domain.entity.CategoryEntity;
import com.lgcns.inspire3_blog.board.domain.entity.HashtagEntity;

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
                    entity.getCategories() == null ? null :
                    entity.getCategories().stream()
                        .map(CategoryEntity::getName)
                        .toList()
                )
                .url(entity.getUrl())
                .hashtags(                
                    entity.getHashtags() == null ? null :
                    entity.getHashtags().stream()
                        .map(HashtagEntity::getName)
                        .toList()
                )
                .createdAt(entity.getCreatedAt())
                .viewCount(entity.getViewCount())
                .likeCount(entity.getLikeCount())
                .build();
    }
}