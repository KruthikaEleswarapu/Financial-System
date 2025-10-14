package com.example.account.controller;

import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        if (account.getBalance() == null) account.setBalance(0.0);
        return repo.save(account);
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<Account> updateBalance(@PathVariable Long id, @RequestParam Double amount) {
        return repo.findById(id).map(acc -> {
            acc.setBalance(acc.getBalance() + amount);
            return ResponseEntity.ok(repo.save(acc));
        }).orElse(ResponseEntity.notFound().build());
    }
}
