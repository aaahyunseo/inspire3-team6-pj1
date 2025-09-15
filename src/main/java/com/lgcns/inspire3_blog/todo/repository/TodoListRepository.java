package com.lgcns.inspire3_blog.todo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lgcns.inspire3_blog.todo.domain.entity.TodoList;
import com.lgcns.inspire3_blog.user.domain.entity.UserEntity;

public interface TodoListRepository extends JpaRepository<TodoList, Long>{
    List<TodoList> findAllByUser(UserEntity user);
    Optional<TodoList> findByTodoIdAndUser(Long todoId, UserEntity user);

    // 오늘 날짜가 포함된 투두리스트 목록 조회 로직
    @Query("SELECT t FROM TodoList t WHERE t.user = :user AND :today BETWEEN t.startDate AND t.endDate")
    List<TodoList> findTodosByUserAndDate(@Param("user") UserEntity user, @Param("today") LocalDate today);

}
