package com.src.visitor;

public class WeightVisitor implements Visitor{

    @Override
    public void visit(BookItem bookItem) {
        System.out.println("bookItem.getWeight() :: " + bookItem.getWeight());
    }

    @Override
    public void visit(FruitItem fruitItem) {
        System.out.println("fruitItem.getWeight() :: " + fruitItem.getWeight());
    }

    @Override
    public void visit(ElectronicItem electronicItem) {
        System.out.println("electronicItem.getWeight() :: " + electronicItem.getWeight());
    }
}
