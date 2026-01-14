package com.kh.todoapi.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.todoapi.todo.dto.request.CreateTodo;
import com.kh.todoapi.todo.dto.request.UpdateTodo;
import com.kh.todoapi.todo.service.TodoService;
import com.kh.todoapi.todo.vo.Todo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodoList() {
        List<Todo> list = todoService.getTodoList();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTodo(@Valid @RequestBody CreateTodo todo) {
    	
        boolean isCreated = todoService.addTodo(todo);
        
        if (isCreated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable(name="id") Long id
    								, @Valid @RequestBody UpdateTodo todo) {

        if (todoService.getTodo(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        todo.setId(id);

        boolean isUpdated = todoService.updateTodo(todo);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(name="id") Long id) {
        if (todoService.getTodo(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isDeleted = todoService.deleteTodo(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
