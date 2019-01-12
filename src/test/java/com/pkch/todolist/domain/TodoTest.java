package com.pkch.todolist.domain;

import com.pkch.todolist.api.account.Account;
import com.pkch.todolist.api.todo.Priority;
import com.pkch.todolist.api.todo.Todo;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoTest {
    Account account;
    @Before
    public void createAccount(){
        account = Account.builder()
                    .name("park")
                    .email("abcde@gmail.com")
                    .password("1234")
                    .age(27)
                    .build();
    }

    @Test
    public void createTodo(){
        Todo todo = Todo.builder()
                    .title("영어공부")
                    .comment("영어단어 15개 외우기")
                    .priority(Priority.NORMAL)
                    .build();
        assertThat(todo).isNotNull();
    }
    // builder를 활용한 생성 Test
    
    @Test
    public void createTodoByAccount(){
        // given (@before에서 account 할당)

        // when
        Todo todo = Todo.builder()
                .title("account test 1")
                .account(account)
                .build();
        // then
        assertThat(todo.getAccount().getName()).isEqualTo(account.getName());
    }
}