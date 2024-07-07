package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Items{
    private String itemNo;
    private LocalDateTime validTo;
    private String itemType;
}
