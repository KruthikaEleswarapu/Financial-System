package com.example.transaction.controller;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    private final String TOPIC = "transactions";

    @PostMapping
    public ResponseEntity<String> transfer(@RequestBody Transaction transaction) {
        transactionRepo.save(transaction);
        kafkaTemplate.send(TOPIC, transaction);
        return ResponseEntity.ok("Transaction successful!");
    }
}
