package com.src.visitor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElectronicItem implements Item{
    private Double price;
    private Double weight;
    private String colour;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
