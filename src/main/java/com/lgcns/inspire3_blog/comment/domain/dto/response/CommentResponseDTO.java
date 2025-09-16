package com.lgcns.inspire3_blog.comment.domain.dto.response;

import java.time.format.DateTimeFormatter;

import com.lgcns.inspire3_blog.comment.domain.entity.Comment;

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
public class CommentResponseDTO {
    private Long commentId;
    private Long userId;
    private String userName;
    private String content;
    private String createdAt;

    public static CommentResponseDTO from(Comment comment) {
        return CommentResponseDTO.builder()
                                .commentId(comment.getCommentId())
                                .userId(comment.getUser().getUserId())
                                .userName(comment.getUser().getName())
                                .content(comment.getContent())
                                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .build();
    }
}
