package com.lgcns.inspire3_blog.todo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire3_blog.todo.domain.entity.TodoList;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

public interface TodoListRepository extends JpaRepository<TodoList, Long>{
    List<TodoList> findAllByUser(UserEntity user);
    Optional<TodoList> findByTodoIdAndUser(Long todoId, UserEntity user);

    List<TodoList> findAllByUserAndCreatedAt(UserEntity user, LocalDate today);
}
