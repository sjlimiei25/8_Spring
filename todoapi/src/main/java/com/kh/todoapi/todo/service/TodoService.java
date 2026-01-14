package com.kh.todoapi.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.todoapi.todo.dto.request.CreateTodo;
import com.kh.todoapi.todo.mapper.TodoMapper;
import com.kh.todoapi.todo.vo.Todo;

import lombok.RequiredArgsConstructor;

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

    public boolean addTodo(CreateTodo todo) {
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
