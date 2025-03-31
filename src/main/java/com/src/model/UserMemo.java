package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMemo {
    private Integer userId;
    private String userMemoName;
    private String colour;
}
