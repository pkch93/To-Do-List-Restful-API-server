package com.pkch.todolist.api.account;

import com.pkch.todolist.api.todo.Todo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity @Data @Builder @AllArgsConstructor
@NoArgsConstructor @EqualsAndHashCode(of = "id")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(length = 20)
    private String password;
    @Column(length = 3)
    private Integer age;
    private LocalDate createdDate;
    @OneToMany(mappedBy = "account")
    private ArrayList<Todo> todoList;
}
