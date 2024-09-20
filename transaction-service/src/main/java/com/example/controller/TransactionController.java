package com.example.controller;

import com.example.Service.TransactionService;
import com.example.dto.TxnRequestDto;
import com.example.dto.TxnStatusDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/transaction-service")
public class TransactionController {
    private static Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;


@PostMapping("/txn")
   ResponseEntity<String> doTransaction(@RequestBody @Valid TxnRequestDto txnRequestDto) throws ExecutionException, InterruptedException {

    logger.info("Starting transaction: {} ",txnRequestDto);
    String result = transactionService.doTransaction(txnRequestDto);
    return ResponseEntity.accepted().body(result);

}

@GetMapping("/status/{txnId}")
    public ResponseEntity<TxnStatusDto> getTxnStatus(@PathVariable String txnId) {
    return ResponseEntity.ok(transactionService.getStatus(txnId));
}
}
