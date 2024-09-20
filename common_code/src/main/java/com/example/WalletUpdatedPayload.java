package com.example;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletUpdatedPayload {

    private String userEmail;

    private Double balance;

    private String requestId;

}
