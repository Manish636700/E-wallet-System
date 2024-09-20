package com.example;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxnCompletedPayload {
    private Long id;

    private Boolean success;

    private String reason;

    private String requestId;

}
