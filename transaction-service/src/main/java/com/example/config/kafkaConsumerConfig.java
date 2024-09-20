package com.example.config;


import com.example.TxnCompletedPayload;
import com.example.TxnInitPayload;
import com.example.TxnStatusEnum;
import com.example.UserCreatedPayload;
import com.example.entity.Transaction;

import com.example.repo.TransactionRepo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.ExecutionException;

@Configuration
public class kafkaConsumerConfig {

   private static Logger logger = LoggerFactory.getLogger(kafkaConsumerConfig.class);

   private static ObjectMapper mapper = new ObjectMapper();

   @Autowired
   private TransactionRepo transactionRepo;



    @KafkaListener(topics = "${txn.completed.topic}",groupId = "txnApp")
    public void consumeTxnCompleted(ConsumerRecord payload) throws JsonProcessingException, ExecutionException, InterruptedException {
        TxnCompletedPayload txnCompletedPayload = mapper.readValue(payload.value().toString(),TxnCompletedPayload.class);
        MDC.put("requestId",txnCompletedPayload.getRequestId());
        logger.info("Read from kafka TxnCompleted : {} ", txnCompletedPayload);


        Transaction transaction = transactionRepo.findById(txnCompletedPayload.getId()).get();
        if(txnCompletedPayload.getSuccess())
        {
            transaction.setStatus(TxnStatusEnum.SUCCESS);
        }
        else {
            transaction.setStatus(TxnStatusEnum.FAILED);
            transaction.setReason(txnCompletedPayload.getReason());
        }
        transactionRepo.save(transaction);
    }

}
