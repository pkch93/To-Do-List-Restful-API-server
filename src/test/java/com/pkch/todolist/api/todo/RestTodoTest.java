package com.pkch.todolist.api.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkch.todolist.api.account.Account;
import com.pkch.todolist.domain.AccountTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class RestTodoTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    Account account;

    @Before
    public void setup(){
        todoRepository.deleteAll();
    }

    @Before
    public void createAccountForTest(){
        String name = "박경철";
        String email = "star_final@naver.com";
        String password = "1234";
        int age = 27;

        account = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .build();
    }

    @Test
    public void createTodo() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        TodoDto todoDto = TodoDto.builder()
                .title("Spring Restful API 스터디")
                .comment("오늘 안에 해야한다.")
                .deadline(now.plusDays(now.until(now, ChronoUnit.DAYS)))
                .priority(Priority.TOP)
                .build();

        Todo todo = modelMapper.map(todoDto, Todo.class);
        // when
        mockMvc.perform(
                post("/api/todo/")
                .content(objectMapper.writeValueAsString(todo))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andDo(print())
        // then
        .andExpect(status().is(201))
        .andExpect(jsonPath("$.title", Matchers.is(todo.getTitle())))
        .andExpect(jsonPath("$.comment", Matchers.is(todo.getComment())))
        .andExpect(jsonPath("$.deadline").exists())
        .andExpect(jsonPath("$.priority", Matchers.is(todo.getPriority().toString())))
        .andDo(document("create",
            links(halLinks(),
                linkWithRel("self").description("생성 된 할일 링크"),
                linkWithRel("create-todo").description("할일 생성 링크"),
                linkWithRel("update-todo").description("할일 수정 링크")
        )));
    }

    @Test
    public void queryTodo() throws Exception {
        // given
        IntStream.range(0, 100)
                .forEach(i -> {
                    Todo todo = Todo.builder()
                            .title("안녕" + i)
                            .comment("테스트용 " + i)
                            .deadline(LocalDateTime.now())
                            .priority(Priority.NORMAL)
                            .build();
                    todoRepository.save(todo);
                });
        // when
        mockMvc.perform(get("/api/todo/")
                    .param("size", "5")
                    .param("page", "2"))
                .andDo(print());
    }
}
