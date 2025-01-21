package com.src.visitor;

public class PriceVisitor implements Visitor{
    @Override
    public void visit(BookItem bookItem) {
        System.out.println("bookItem.getPrice() :: " + bookItem.getPrice());
    }

    @Override
    public void visit(FruitItem fruitItem) {
        System.out.println("fruitItem.getPrice() :: " + fruitItem.getPrice());
    }

    @Override
    public void visit(ElectronicItem electronicItem) {
        System.out.println("electronicItem.getPrice() :: " + electronicItem.getPrice());
    }
}
