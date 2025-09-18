package com.lgcns.inspire3_blog.todo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoListUpdateRequestDTO {
    private Long userId;
    private Long todoId;
    private String content;
}
