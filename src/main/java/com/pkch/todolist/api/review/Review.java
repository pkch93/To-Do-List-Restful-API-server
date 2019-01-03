package com.pkch.todolist.api.review;

import com.pkch.todolist.api.todo.Todo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;
    private LocalDateTime createdDate;
    @OneToOne
    private Todo todo;
}
