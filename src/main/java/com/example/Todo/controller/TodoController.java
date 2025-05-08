package com.example.Todo.controller;

import com.example.Todo.model.TodoItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class TodoController {

    private final List<TodoItem> todos = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", todos);
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String task) {
        todos.add(new TodoItem(idGenerator.getAndIncrement(), task, false)); // false = not completed (Pending)
        return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String toggleStatus(@PathVariable int id) {
        for (TodoItem todo : todos) {
            if (todo.getId() == id) {
                todo.setCompleted(!todo.isCompleted()); // Toggle the completed status
                break;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable int id) {
        todos.removeIf(todo -> todo.getId() == id);
        return "redirect:/";
    }
}
