package com.pkch.todolist.api.account;

import com.pkch.todolist.api.todo.Todo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter @EqualsAndHashCode(of = "id")
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

    @Builder
    public Account(String name, String email, String password, Integer age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.createdDate = LocalDate.now();
        this.todoList = new ArrayList<>();
    }
}
