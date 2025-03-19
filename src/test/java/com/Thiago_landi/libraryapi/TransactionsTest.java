package com.Thiago_landi.libraryapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Thiago_landi.libraryapi.service.TransactionService;

@SpringBootTest
public class TransactionsTest {

	@Autowired
	private TransactionService service;
	
	 /**
     * Commit -> confirmar as alterações
     * Rollback -> desfazer as alterações
     */
	
	@Test
	public void Transaction() {
		service.execute();
	}
	
}
