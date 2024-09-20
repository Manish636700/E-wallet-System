package com.example.config;

import com.example.UserCreatedPayload;
import com.example.WalletUpdatedPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class kafkaConsumerConfig {

   private static Logger logger = LoggerFactory.getLogger(kafkaConsumerConfig.class);

   private static ObjectMapper mapper = new ObjectMapper();

   @Autowired
   private JavaMailSender mailSender;

    @KafkaListener(topics="${user.created.topic}",groupId = "email")
    public void consumeUserCreatedTopic(ConsumerRecord payload) throws JsonProcessingException {
        UserCreatedPayload userCreatedPayload=mapper.readValue(payload.value().toString(),UserCreatedPayload.class);

        MDC.put("requestId",userCreatedPayload.getRequestId());

        logger.info("Read from kafka : {} ", userCreatedPayload);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("jbdl.ewallet@gmail.com");
        mailMessage.setSubject("Welcome"+userCreatedPayload.getUserName());
        mailMessage.setText("Hi" + userCreatedPayload.getUserName()+",Welcome in jbdl wallet");
        mailMessage.setCc("manishmittal296@gmail.com");
        mailMessage.setTo(userCreatedPayload.getUserEmail());
        mailSender.send(mailMessage);

        MDC.clear();
    }

    @KafkaListener(topics="${wallet.updated.topic}",groupId = "email")
    public void consumeWalletUpdatedTopic(ConsumerRecord payload) throws JsonProcessingException {
    WalletUpdatedPayload walletUpdatedPayload=mapper.readValue(payload.value().toString(),WalletUpdatedPayload.class);

        MDC.put("requestId", walletUpdatedPayload.getRequestId());

        logger.info("Read from kafka : {} ", walletUpdatedPayload);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("jbdl.ewallet@gmail.com");
        mailMessage.setSubject("JBDL Wallet Updated");
        mailMessage.setText("Hi,you updated balance is " +walletUpdatedPayload.getBalance());
        mailMessage.setCc("manishmittal296@gmail.com");
        mailMessage.setTo(walletUpdatedPayload.getUserEmail());
        mailSender.send(mailMessage);
        MDC.clear();
    }



}
