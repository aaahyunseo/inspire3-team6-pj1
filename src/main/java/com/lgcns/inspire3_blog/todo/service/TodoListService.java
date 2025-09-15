package com.lgcns.inspire3_blog.todo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lgcns.inspire3_blog.todo.domain.dto.request.TodoListCreateRequestDTO;
import com.lgcns.inspire3_blog.todo.domain.dto.request.TodoListUpdateRequestDTO;
import com.lgcns.inspire3_blog.todo.domain.dto.response.TodoListResponseData;
import com.lgcns.inspire3_blog.todo.domain.entity.TodoList;
import com.lgcns.inspire3_blog.todo.repository.TodoListRepository;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;
import com.lgcns.inspire3_blog.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoListService {
    
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;

    /*
     * 유저의 투두 리스트 조회하기
     */
    public TodoListResponseData getTodoList(Long userId) {
        UserEntity user = findUserById(userId);
        
        List<TodoList> todoList = todoListRepository.findAllByUser(user);

        return TodoListResponseData.from(todoList);
    }

    /*
     * 유저의 오늘 투두 리스트 조회하기
     */
    public TodoListResponseData getTodoListToday(Long userId) {
        UserEntity user = findUserById(userId);
        LocalDate today = LocalDate.now();
        
        List<TodoList> todoList = todoListRepository.findTodosByUserAndDate(user, today);

        return TodoListResponseData.from(todoList);
    }

    /*
     * 투두 등록하기
     */
    public void createTodo(TodoListCreateRequestDTO request) {
        UserEntity user = findUserById(request.getUserId());

        TodoList newTodo = TodoList.builder()
                                    .user(user)
                                    .content(request.getContent())
                                    .startDate(request.getStartDate())
                                    .endDate(request.getEndDate())
                                    .done(false)
                                    .build();
                                   
        todoListRepository.save(newTodo);
    }
    
    /*
     * 투두 수정하기
     */
    @Transactional
    public void updateTodo(TodoListUpdateRequestDTO request) {
        UserEntity user = findUserById(request.getUserId());
        TodoList updateTodo = findTodoByIdAndUser(request.getTodoId(), user);

        // 부분 수정 가능하도록 로직 설계
        if (request.getContent() != null) updateTodo.setContent(request.getContent());
        if (request.getStartDate() != null) updateTodo.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) updateTodo.setEndDate(request.getEndDate());

        todoListRepository.save(updateTodo);
    }

    /*
     * 투두 삭제하기
     */
    public void deleteTodo(Long todoId, Long userId) {
        UserEntity user = findUserById(userId);
        TodoList deleteTodo = findTodoByIdAndUser(todoId, user);
        todoListRepository.delete(deleteTodo);
    }

    /*
     * 투두 상태 변환하기 (false->ture , true->false)
     */
    @Transactional
    public void updateTodoStatus(Long todoId, Long userId) {
        UserEntity user = findUserById(userId);
        TodoList todo = findTodoByIdAndUser(todoId, user);

        todo.setDone(!todo.isDone());
        
        todoListRepository.save(todo);
    }

    /*
     * 유저 조회 메서드
     */
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("ID에 해당하는 유저를 찾을 수 없습니다."));
    }

    /*
     * 투두 조회 메서드
     */
    public TodoList findTodoByIdAndUser(Long todoId, UserEntity user) {
        return todoListRepository.findByTodoIdAndUser(todoId, user)
                .orElseThrow( () -> new RuntimeException("해당하는 Todo를 찾을 수 없습니다."));
    }

}
