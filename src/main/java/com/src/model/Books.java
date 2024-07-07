package com.src.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Books {
    private String name;
    private int releaseYear;
    private String isbn;
    private int price;

}
