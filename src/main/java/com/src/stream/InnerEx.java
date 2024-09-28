package com.src.stream;

public class InnerEx {

    static class InnerStatic {
        private class Private {
            private static int j = 20;
            private int i = 10;

            private String evenOdd(int num) {
                i = i+ j;
                return ((num % 2 == 0) ? "even" : "odd");
            }
        }
    }


    public static void main(String[] args) {

        //static Inner class
        InnerEx.InnerStatic.Private sip = new InnerEx.InnerStatic().new Private();
        String ans = sip.evenOdd(5);
        System.out.println("evenOdd :: " + ans + ":: i="+sip.i +" ::j="+ InnerStatic.Private.j);
        //evenOdd :: odd:: i=30 ::j=20*/

        //Anonymous Inner class

        class ABCD {
            public void print() {
                System.out.println("ABCD 1...");
            }
        }

        ABCD abcd = new ABCD() {
            @Override
            public void print() {
                System.out.println("ABCD 2...");
            }
        };
        abcd.print(); //ABCD 2...
    }


}
