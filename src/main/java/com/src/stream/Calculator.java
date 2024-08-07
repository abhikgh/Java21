package com.src.stream;

import java.util.ResourceBundle;

public class Calculator {
    public int multiply(int a, int b) {
        return a * b;
    }

    public int multiplyUsingProperties() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        Integer m1 = Integer.parseInt(resourceBundle.getString("m1"));
        if(m1==123){
            return m1;
        }else{
            return 100;
        }
    }

    public int checkUsingInput(int m1) {
        if(m1==123){
            return m1;
        }else{
            return 100;
        }
    }
}
