package com.pkch.todolist.api.account;

import com.pkch.todolist.Application;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AccountResource extends Resource<Account> {

    public AccountResource(Account content, Link... links) {
        super(content, links);
        Link link = new Link("http://localhost:8080/", "index");
        this.add(link);
    }
}