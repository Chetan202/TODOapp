package com.api.redis.demo.controller;


import com.api.redis.demo.entity.Todo;
import com.api.redis.demo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {
    @Autowired
    private TodoService svc;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(svc.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return svc.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(svc.createTodo(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id,
                                           @Valid @RequestBody Todo todo) {
        try {
            return ResponseEntity.ok(svc.updateTodo(id, todo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        svc.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Todo>> getTodosByStatus(@PathVariable Boolean completed) {
        return ResponseEntity.ok(svc.getTodosByStatus(completed));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String keyword) {
        return ResponseEntity.ok(svc.searchTodos(keyword));
    }
}
