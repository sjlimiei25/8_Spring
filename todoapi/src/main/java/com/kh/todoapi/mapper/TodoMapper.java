package com.kh.todoapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.todoapi.dto.request.CreateTodo;
import com.kh.todoapi.vo.Todo;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();

    int save(CreateTodo todo);

    int updateStatus(Todo todo);

    int delete(Long id);

    Todo findById(Long id);
}
