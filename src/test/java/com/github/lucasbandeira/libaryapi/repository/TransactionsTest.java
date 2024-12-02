package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionsTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void simpleTransaction(){
        transactionService.execute();
    }

    @Test
    void manegedStateTransaction(){
        transactionService.updateWithoutUpdating();
    }
}
