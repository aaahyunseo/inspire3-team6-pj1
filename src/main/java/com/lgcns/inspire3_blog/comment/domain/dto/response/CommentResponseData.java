package com.lgcns.inspire3_blog.comment.domain.dto.response;

import java.util.List;
import java.util.stream.Collectors;

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
public class CommentResponseData {
    private List<CommentResponseDTO> commentList;

    public static CommentResponseData from(List<Comment> comments) {
        return CommentResponseData.builder()
                                .commentList(
                                    comments.stream()
                                        .map(CommentResponseDTO::from)
                                        .collect(Collectors.toList()))
                                .build();
    }
}
