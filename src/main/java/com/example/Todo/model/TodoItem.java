package com.example.Todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoItem {
    private int id;
    private String task;
    private boolean completed; // false = pending, true = completed
}
