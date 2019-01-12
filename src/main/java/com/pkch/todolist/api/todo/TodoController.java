package com.pkch.todolist.api.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/todo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TodoController {

    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public ResponseEntity<PagedResources<Todo>> myTodoList(@PageableDefault Pageable pageable){
        Page<Todo> todoList = todoRepository.findAll(pageable);
        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(),
                todoList.getNumber(), todoList.getTotalElements());
        PagedResources<Todo> resources = new PagedResources<>(todoList.getContent(), pageMetadata);
        resources.add(linkTo(methodOn(TodoController.class).myTodoList(pageable)).withSelfRel());
        return ResponseEntity.ok(resources);
    } // 할일 목록 가져오기

    @GetMapping("/{id}")
    public ResponseEntity<TodoResource> todoDetails(@PathVariable("id") Todo todo){
        TodoResource todoResource = new TodoResource(todo,
                linkTo(TodoController.class).slash(todo.getId()).withSelfRel());
        return ResponseEntity.ok(todoResource);
    } // 할일 상세 정보 보기

    @PostMapping("/")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo){
        Todo savedTodo = todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
    } // 할일 등록

    // 할일 수정, 삭제, 진행상황 등록 추가해야함 + 테스트 코드
}
