package com.kh.todoapi.controller;

import com.kh.todoapi.service.TodoService;
import com.kh.todoapi.vo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> addTodo(@RequestBody Todo todo) {
        if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean isCreated = todoService.addTodo(todo);
        if (isCreated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable(name="id") Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || (!status.equals("IN_PROGRESS") && !status.equals("COMPLETED"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (todoService.getTodo(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isUpdated = todoService.updateTodo(id, status);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
