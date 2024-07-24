package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Order {

    private String orderNumber;
    private Integer orderAmount;
    private LocalDateTime validFrom;
}
