package com.lgcns.inspire3_blog.mypage.dto;

import java.time.format.DateTimeFormatter;

import com.lgcns.inspire3_blog.board.domain.entity.BoardEntity;

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
public class UserWriteBoardResponseDTO {
    private Integer boardId;
    private String title;
    private String userName;
    private String createdAt;
    private String category;

    public static UserWriteBoardResponseDTO from(BoardEntity board) {
        return UserWriteBoardResponseDTO.builder()
                                    .boardId(board.getBoardId())
                                    .title(board.getTitle())
                                    .userName(board.getUser().getName())
                                    .createdAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                    .category(board.getCategory())
                                    .build();
    }
}