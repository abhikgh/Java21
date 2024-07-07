package com.src.stream;

/*
Interface
------------
1. variables :: public, static, final
2. public abstract method
3. public static method
4. private static method (can be called from within Interface default/static methods)
5. default method (gives default implementation of the method)
6. private method (can be called from within Interface default/private methods)
 */
public sealed interface SealedEx permits ChildInt {

    //2. public abstract method
    void mul(int a, int b);

    //3. public static method
    static void mod(int a, int b) {
        System.out.println("Some public static method in Root Interface, available to all extending interfaces and implementing classes.");
        div(a, b); //calling private static method
        System.out.print("Answer by Static method = ");
        System.out.println(a % b);
    }

    //4. private static method
    private static void div(int a, int b) {
        System.out.print("Answer by Private static method = ");
        System.out.println(a / b);
    }

    //5. default method
    default void add(int a, int b) {
        sub(a, b); //calling private method
        div(a, b); //calling private static method
        System.out.print("Answer by Default method = ");
        System.out.println(a + b);
    }

    //6. private
    private void sub(int a, int b) {
        System.out.print("Answer by Private method = ");
        System.out.println(a - b);
    }

}

