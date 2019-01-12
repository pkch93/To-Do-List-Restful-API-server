package com.pkch.todolist.domain;

import com.pkch.todolist.api.account.Account;
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
}