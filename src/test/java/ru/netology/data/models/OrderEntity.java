package ru.netology.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private String id;
    private LocalDateTime created;
    private String creditId;
    private String paymentId;

}
