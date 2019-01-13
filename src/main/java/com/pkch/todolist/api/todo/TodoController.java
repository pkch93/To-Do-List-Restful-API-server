package com.pkch.todolist.api.todo;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/todo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TodoController {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public TodoController(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<PagedResources<Todo>> myTodoList(@PageableDefault Pageable pageable) {
        Page<Todo> todoList = todoRepository.findAll(pageable);
        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(),
                todoList.getNumber(), todoList.getTotalElements());
        PagedResources<Todo> resources = new PagedResources<>(todoList.getContent(), pageMetadata);
        resources.add(
                linkTo(methodOn(TodoController.class).myTodoList(pageable)).withSelfRel()
        );

        return ResponseEntity.ok(resources);
    } // 할일 목록 가져오기

    @GetMapping("/{id}")
    public ResponseEntity<TodoResource> todoDetails(@PathVariable("id") Todo todo) {
        TodoResource todoResource = new TodoResource(todo);
        todoResource.add(
                linkTo(TodoController.class).withRel("update-todo"),
                linkTo(TodoController.class).withRel("delete-todo")
        );
        return ResponseEntity.ok(todoResource);
    } // 할일 상세 정보 보기

    @PostMapping("/")
    public ResponseEntity createTodo(@RequestBody @Valid TodoDto todoDto, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return ResponseEntity.badRequest().build();
        }
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);

        ControllerLinkBuilder linkBuilder = linkTo(TodoController.class).slash(savedTodo.getId());
        URI uri = linkBuilder.toUri();
        TodoResource todoResource = new TodoResource(savedTodo);
        todoResource.add(
                linkTo(TodoController.class).withRel("create-todo"),
                linkBuilder.withRel("update-todo")
        );
        return ResponseEntity.created(uri).body(todoResource);
    } // 할일 등록

    @PutMapping("/{id}")
    public ResponseEntity updateTodo(@PathVariable Long id,
                                        @RequestBody @Valid TodoDto todoDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Todo target = todoRepository.findById(id).orElseThrow(NullPointerException::new);
        target.update(todoDto);
        Todo updatedTodo = todoRepository.save(target);

        TodoResource todoResource = new TodoResource(updatedTodo);
        todoResource.add(
                linkTo(TodoController.class).withRel("create-todo"),
                linkTo(TodoController.class).slash(updatedTodo.getId()).withRel("details-todo"),
                linkTo(TodoController.class).slash(updatedTodo.getId()).withRel("update-todo")
        );
        return ResponseEntity.ok(todoResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable Long id){
        todoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
