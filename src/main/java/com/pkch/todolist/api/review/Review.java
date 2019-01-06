package com.pkch.todolist.api.review;

import com.pkch.todolist.api.todo.Todo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;
    private LocalDateTime createdAt;
    @OneToOne
    private Todo todo;

    @Builder
    public Review(String title, String content, Todo todo) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.todo = todo;
    }
}
