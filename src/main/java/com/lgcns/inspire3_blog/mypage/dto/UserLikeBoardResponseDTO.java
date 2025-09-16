package com.lgcns.inspire3_blog.mypage.dto;

import java.time.format.DateTimeFormatter;

import com.lgcns.inspire3_blog.board.domain.entity.BoardLike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLikeBoardResponseDTO {
    private Integer boardId;
    private String title;
    private String userName;
    private String createdAt;
    private String category;

    public static UserLikeBoardResponseDTO from(BoardLike boardLike) {
        return UserLikeBoardResponseDTO.builder()
                                    .boardId(boardLike.getBoard().getBoardId())
                                    .title(boardLike.getBoard().getTitle())
                                    .userName(boardLike.getUser().getName())
                                    .createdAt(boardLike.getBoard().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                    .category(boardLike.getBoard().getCategory())
                                    .build();
    }
}
