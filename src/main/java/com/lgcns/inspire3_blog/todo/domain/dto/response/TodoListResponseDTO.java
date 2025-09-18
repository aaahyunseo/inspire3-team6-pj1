package com.lgcns.inspire3_blog.todo.domain.dto.response;

import java.time.LocalDate;

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
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean done;

    public static TodoListResponseDTO from(TodoList todo) {
        return TodoListResponseDTO.builder()
                                .todoId(todo.getTodoId())
                                .content(todo.getContent())
                                .startDate(todo.getStartDate())
                                .endDate(todo.getEndDate())
                                .done(todo.isDone())
                                .build();
    }
}
