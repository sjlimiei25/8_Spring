package com.kh.todoapi.service;

import com.kh.todoapi.mapper.TodoMapper;
import com.kh.todoapi.vo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;

    public List<Todo> getTodoList() {
        return todoMapper.findAll();
    }

    public Todo getTodo(Long id) {
        return todoMapper.findById(id);
    }

    public boolean addTodo(Todo todo) {
        return todoMapper.save(todo) > 0;
    }

    public boolean updateTodo(Long id, String status) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setStatus(status);
        return todoMapper.updateStatus(todo) > 0;
    }

    public boolean deleteTodo(Long id) {
        return todoMapper.delete(id) > 0;
    }
}
