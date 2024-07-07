package com.src.stream;

public class ChildIntImpl implements ChildInt{

    public static void main(String[] args) {
        SealedEx sealedEx = new ChildIntImpl();
        sealedEx.mul(2,3); // public abstract (implemented)
        SealedEx.mod(2, 3); // public static


    }

    @Override
    public void mul(int a, int b) {
        System.out.print("Implementing...");
        System.out.println(a * b);
    }
}
