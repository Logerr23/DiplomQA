package ru.netology.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private String id;
    private int amount;
    private LocalDateTime created;
    private String status;
    private String transactionId;


}
