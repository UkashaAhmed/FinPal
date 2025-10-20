package com.finpal.web;

import com.finpal.domain.Account;
import com.finpal.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {
    private final AccountRepo accountRepo;
    @GetMapping public List<Account> list() { return accountRepo.findAll(); }
}
