package com.api.redis.demo.service;

import com.api.redis.demo.entity.Todo;
import com.api.redis.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repo;

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return repo.findById(id);
    }

    public Todo createTodo(Todo todo) {
        return repo.save(todo);
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.getCompleted());

        return repo.save(todo);
    }

    public void deleteTodo(Long id) {
        repo.deleteById(id);
    }

    public List<Todo> getTodosByStatus(Boolean completed) {
        return repo.findByCompleted(completed);
    }

    public List<Todo> searchTodos(String keyword) {
        return repo.findByTitleContainingIgnoreCase(keyword);
    }
}