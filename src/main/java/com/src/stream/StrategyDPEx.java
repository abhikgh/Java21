package com.src.stream;

import java.math.BigDecimal;

interface Discounter{
    BigDecimal applyDiscount(BigDecimal input);

    static Discounter applyChristmasDiscount(){
        return input -> input.multiply(BigDecimal.valueOf(0.9));
    }

    static Discounter newYearDiscounter() {
        return amount -> amount.multiply(BigDecimal.valueOf(0.8));
    }

    static Discounter easterDiscounter() {
        return amount -> amount.multiply(BigDecimal.valueOf(0.5));
    }
}
public class StrategyDPEx {
    public static void main(String[] args) {
        BigDecimal priceAfterDiscount = Discounter.applyChristmasDiscount().applyDiscount(BigDecimal.valueOf(10));
        System.out.println(priceAfterDiscount);
    }


}
