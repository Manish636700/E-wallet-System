package com.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PGPaymentStatusDTO {
    private String status;
    private Long userId;
    private Double amount;

}
