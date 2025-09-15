package com.lgcns.inspire3_blog.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgcns.inspire3_blog.todo.domain.dto.request.TodoListCreateRequestDTO;
import com.lgcns.inspire3_blog.todo.domain.dto.request.TodoListUpdateRequestDTO;
import com.lgcns.inspire3_blog.todo.domain.dto.response.TodoListResponseData;
import com.lgcns.inspire3_blog.todo.service.TodoListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoListController {
    
    private final TodoListService todoListService;

    /*
     * 유저의 투두 리스트 조회하기
     */
    @GetMapping("/list")
    public ResponseEntity<TodoListResponseData> getTodoList(@RequestParam("userId") Long userId) {
        TodoListResponseData response = todoListService.getTodoList(userId);
        return new ResponseEntity<TodoListResponseData>(response, HttpStatus.OK);
    }

    /*
     * 유저의 오늘 투두 리스트 조회하기
     */
    @GetMapping("/list/today")
    public ResponseEntity<TodoListResponseData> getTodoListToday(@RequestParam("userId") Long userId) {
        TodoListResponseData response = todoListService.getTodoListToday(userId);
        return new ResponseEntity<TodoListResponseData>(response, HttpStatus.OK);
    }

    /*
     * 투두 등록하기
     */
    @PostMapping("/register")
    public ResponseEntity<Void> createTodo(@RequestBody TodoListCreateRequestDTO request) {
        todoListService.createTodo(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
     * 투두 수정하기
     */
    @PatchMapping("/patch")
    public ResponseEntity<Void> updateTodo(@RequestBody TodoListUpdateRequestDTO request) {
        todoListService.updateTodo(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 투두 삭제하기
     */
    @DeleteMapping("/{todoId}/delete")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todoId") Long todoId, @RequestParam("userId") Long userId) {
        todoListService.deleteTodo(todoId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * 투두 상태 변환하기 (false->ture , true->false)
     */
    @PatchMapping("/{todoId}/status")
    public ResponseEntity<Void> updateTodoStatus(@PathVariable("todoId") Long todoId, @RequestParam("userId") Long userId) {
        todoListService.updateTodoStatus(todoId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
     
}
