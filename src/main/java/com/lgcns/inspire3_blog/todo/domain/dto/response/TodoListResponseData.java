package com.lgcns.inspire3_blog.todo.domain.dto.response;

import java.util.List;
import java.util.stream.Collectors;

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
public class TodoListResponseData {
    private List<TodoListResponseDTO> todoList;

    public static TodoListResponseData from(List<TodoList> todos) {
        return TodoListResponseData.builder()
                                .todoList(
                                    todos.stream()
                                        .map(TodoListResponseDTO::from)
                                        .collect(Collectors.toList()))
                                .build();
    }
}
