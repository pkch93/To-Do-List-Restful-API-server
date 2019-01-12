package com.pkch.todolist.api.todo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class TodoResource extends Resource<Todo> {

    public TodoResource(Todo content, Link... links) {
        super(content, links);
    }
}
