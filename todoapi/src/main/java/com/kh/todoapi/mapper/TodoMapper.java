package com.kh.todoapi.mapper;

import com.kh.todoapi.vo.Todo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();

    int save(Todo todo);

    int updateStatus(Todo todo);

    int delete(Long id);

    Todo findById(Long id);
}
