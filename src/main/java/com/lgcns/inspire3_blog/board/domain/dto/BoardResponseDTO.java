package com.lgcns.inspire3_blog.board.domain.dto;

import java.time.format.DateTimeFormatter;
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
    private Long userId;
    private String title;
    private String content;
    private String category;
    private String url;
    private List<String> hashtags;
    private String createdAt;
    private Integer viewCount;
    private Integer likeCount;

    public static BoardResponseDTO from(BoardEntity entity) {
        return BoardResponseDTO.builder()
                .boardId(entity.getBoardId())
                .userId(entity.getUser().getUserId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory())
                .url(entity.getUrl())
                .hashtags(                
                    entity.getBoardHashtags() == null ? null :
                    entity.getBoardHashtags().stream()
                        .map(boardHashtag -> boardHashtag.getHashtag().getName())
                        .toList()
                )
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .viewCount(entity.getViewCount())
                .likeCount(entity.getLikeCount())
                .build();
    }
}