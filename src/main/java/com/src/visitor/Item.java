package com.src.visitor;

//Interface for all inventory items
//Each item will accept the Visitor
public interface Item {
    void accept(Visitor visitor);
}
