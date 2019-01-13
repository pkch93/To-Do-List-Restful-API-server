package com.pkch.todolist.api.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class TodoDto {
    @NotEmpty
    private String title;
    private String comment;
    @NotNull
    private Priority priority;
    @NotNull
    private LocalDateTime deadline;
}
