package com.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCreatedPayload {
    private Long userId;
    private String userName;
    private String userEmail;
    private String requestId;
}
