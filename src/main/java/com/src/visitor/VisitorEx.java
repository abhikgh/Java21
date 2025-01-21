package com.src.visitor;

/**
 * Visitor Design Pattern
 *
 * behavioral design pattern that allows us to add further operations to objects without modifying their structure
 */

public class VisitorEx {

    public static void main(String[] args) {

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new BookItem(19.99, 1.2, "black"));
        cart.addItem(new FruitItem(2.99, 1.5, "red"));
        cart.addItem(new ElectronicItem(2.99, 1.5, "blue"));

        Visitor priceVisitor = new PriceVisitor();
        Visitor weightVisitor = new WeightVisitor();
        Visitor colourVisitor = new ColourVisitor();

        cart.applyVisitor(priceVisitor);
        cart.applyVisitor(weightVisitor);
        cart.applyVisitor(colourVisitor);



    }
}
