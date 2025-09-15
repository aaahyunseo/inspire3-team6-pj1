package com.lgcns.inspire3_blog.todo.domain.dto.request;

import java.time.LocalDate;

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
public class TodoListCreateRequestDTO {
    private Long userId;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
}
