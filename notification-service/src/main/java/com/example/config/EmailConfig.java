package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("jbdl.ewallet@gmail.com");
        mailSender.setPassword("zopoiuvqpotrrdad");
        mailSender.setPort(587);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", true);
        return mailSender;


    }
}
