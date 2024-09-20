package com.example.config;

import com.example.Service.WalletService;
import com.example.TxnInitPayload;
import com.example.UserCreatedPayload;
import com.example.entity.Wallet;
import com.example.repo.WalletRepo;
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
   private WalletRepo walletRepo;

   @Autowired
   private WalletService walletService;


    @KafkaListener(topics="${user.created.topic}",groupId = "WalletApp")
    public void consumeUserCreatedTopic(ConsumerRecord payload) throws JsonProcessingException {
        UserCreatedPayload userCreatedPayload=mapper.readValue(payload.value().toString(),UserCreatedPayload.class);
        MDC.put("requestId",userCreatedPayload.getRequestId());
        logger.info("Read from kafka : {} ", userCreatedPayload);
        Wallet wallet = new Wallet();
        wallet.setBalance(100.00);
        wallet.setUserId(userCreatedPayload.getUserId());
        wallet.setUserEmail(userCreatedPayload.getUserEmail());
        walletRepo.save(wallet);
        MDC.clear();
    }

    @KafkaListener(topics = "${txn.init.topic}",groupId = "WalletApp")
    public void consumeTxnInit(ConsumerRecord payload) throws JsonProcessingException, ExecutionException, InterruptedException {
        TxnInitPayload txnInitPayload = mapper.readValue(payload.value().toString(),TxnInitPayload.class);
        MDC.put("requestId",txnInitPayload.getRequestId());
        logger.info("Read from kafka : {} ", txnInitPayload);
        walletService.walletTxn(txnInitPayload);


    }

}
