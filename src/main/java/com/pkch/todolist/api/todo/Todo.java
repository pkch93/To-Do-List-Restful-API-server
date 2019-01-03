package com.pkch.todolist.api.todo;

import com.pkch.todolist.api.account.Account;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;


@Entity
@Data @Builder @EqualsAndHashCode(of = "id")
@NoArgsConstructor @AllArgsConstructor
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String title;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Max(100) @Min(0)
    private Integer progress;
    private LocalDateTime deadline;
    private LocalDateTime createdDate;
    @ManyToOne
    private Account account;
}
