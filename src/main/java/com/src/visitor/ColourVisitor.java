package com.src.visitor;

public class ColourVisitor implements Visitor{

    @Override
    public void visit(BookItem bookItem) {
        System.out.println("bookItem.getColour() :: " + bookItem.getColour());
    }

    @Override
    public void visit(FruitItem fruitItem) {
        System.out.println("fruitItem.getColour() :: " + fruitItem.getColour());
    }

    @Override
    public void visit(ElectronicItem electronicItem) {
        System.out.println("electronicItem.getColour() :: " + electronicItem.getColour());
    }
}
