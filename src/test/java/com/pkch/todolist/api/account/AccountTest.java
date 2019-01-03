package com.pkch.todolist.api.account;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void createAccount(){
        Account account = Account.builder()
                        .name("pkch")
                        .build();
        assertThat(account).isNotNull();
    }

    @Test
    public void isJavaBean(){
        // given
        String name = "pkch";
        int age = 27;
        // when
        Account account = new Account();
        account.setName(name);
        account.setAge(age);
        // then
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getAge()).isEqualTo(age);
    }
}