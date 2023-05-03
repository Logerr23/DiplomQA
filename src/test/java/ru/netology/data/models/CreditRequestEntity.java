package ru.netology.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestEntity {
    private String id;
    private String bankId;
    private LocalDateTime created;
    private String status;

}
