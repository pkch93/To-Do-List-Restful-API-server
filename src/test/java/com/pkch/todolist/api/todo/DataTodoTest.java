package com.pkch.todolist.api.todo;

import com.pkch.todolist.api.account.Account;
import com.pkch.todolist.api.account.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataTodoTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void addAccount(){
        Account account = Account.builder()
                .name("박경철")
                .email("star_final@naver.com")
                .password("1234")
                .age(27)
                .build();
        accountRepository.save(account);
    }

    @Test
    public void createTodo(){
        // given
        LocalDateTime now = LocalDateTime.now();
        Account account = accountRepository.findById(1l).get();
        Todo todo = Todo.builder()
                .title("Spring Restful API 스터디")
                .comment("오늘 안에 해야한다.")
                .deadline(now.plusDays(now.until(now, ChronoUnit.DAYS)))
                .priority(Priority.TOP)
                .account(account)
                .build();
        // when
        Todo result = todoRepository.save(todo);
        todoRepository.flush();
        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(todo.getTitle());
        assertThat(result.getProgress()).isEqualTo(0);
    }
}
