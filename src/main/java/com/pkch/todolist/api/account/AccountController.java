package com.pkch.todolist.api.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/")
    public ResponseEntity<AccountResource> join(@RequestBody Account account){

        Account joinedAccount = accountRepository.save(account);
        AccountResource accountResource = new AccountResource(joinedAccount ,
                linkTo(methodOn(AccountController.class).join(joinedAccount)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResource);
    }
}
