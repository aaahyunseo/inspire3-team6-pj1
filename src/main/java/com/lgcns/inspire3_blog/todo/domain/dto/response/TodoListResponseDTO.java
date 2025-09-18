package com.lgcns.inspire3_blog.todo.domain.dto.response;

import java.time.format.DateTimeFormatter;

import com.lgcns.inspire3_blog.todo.domain.entity.TodoList;

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
public class TodoListResponseDTO {
    private Long todoId;
    private String content;
    private String date;
    private boolean done;

    public static TodoListResponseDTO from(TodoList todo) {
        return TodoListResponseDTO.builder()
                                .todoId(todo.getTodoId())
                                .content(todo.getContent())
                                .date(todo.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .done(todo.isDone())
                                .build();
    }
}
