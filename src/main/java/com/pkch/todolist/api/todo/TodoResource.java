package com.pkch.todolist.api.todo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class TodoResource extends Resource<Todo> {

    public TodoResource(Todo todo, Link... links) {
        super(todo, links);
        add(linkTo(TodoController.class).slash(todo.getId()).withSelfRel());
    }
}
