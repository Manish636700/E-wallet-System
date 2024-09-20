package com.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMoneyRequest {

    private Long merchantId;
    private Double amount;
    private Long userId;

}
