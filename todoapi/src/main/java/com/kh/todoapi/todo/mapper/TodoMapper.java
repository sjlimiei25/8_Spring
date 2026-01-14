package com.kh.todoapi.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.todoapi.todo.dto.request.CreateTodo;
import com.kh.todoapi.todo.vo.Todo;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();

    int save(CreateTodo todo);

    int updateStatus(Todo todo);

    int delete(Long id);

    Todo findById(Long id);
}
