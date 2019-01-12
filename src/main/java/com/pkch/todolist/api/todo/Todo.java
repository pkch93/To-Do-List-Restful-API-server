package com.pkch.todolist.api.todo;

import com.pkch.todolist.api.account.Account;
import lombok.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Max(100) @Min(0)
    private Integer progress = 0;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    @ManyToOne
    private Account account;

    @Builder
    public Todo(String title, String comment, @Nullable Priority priority, LocalDateTime deadline, Account account) {
        this.title = title;
        this.comment = comment;
        this.priority = priority;
        this.deadline = deadline;
        this.createdAt = LocalDateTime.now();
        this.account = account;
    }
}


