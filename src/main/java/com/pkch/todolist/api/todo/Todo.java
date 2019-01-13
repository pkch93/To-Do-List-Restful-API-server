package com.pkch.todolist.api.todo;

import com.pkch.todolist.api.account.Account;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(of = "id") @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String title;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;
    private int progress = 0;
    private LocalDateTime deadline;
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private Account account;

    public void update(TodoDto todoDto){
        this.title = todoDto.getTitle();
        this.comment = todoDto.getComment();
        this.deadline = todoDto.getDeadline();
        this.priority = todoDto.getPriority();
    }

    @Builder
    public Todo(String title, String comment, @Nullable Priority priority, LocalDateTime deadline, Account account) {
        this.title = title;
        this.comment = comment;
        this.priority = priority;
        this.deadline = deadline;
        this.account = account;
    }
}


