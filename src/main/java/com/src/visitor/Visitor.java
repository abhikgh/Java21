package com.src.visitor;

//Visitor will visit all the items
public interface Visitor {
    void visit(BookItem bookItem);
    void visit(FruitItem fruitItem);
    void visit(ElectronicItem electronicItem);
}
