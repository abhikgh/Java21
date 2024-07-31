package com.src.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldTest {

    private String pri;
    protected String pro;
    public String pub;

    private void somePrivateMethod(){}

    private void somePrivateMethod2(String string){
        System.out.println(string.toUpperCase());
    }

    class InnerFieldTest{
        private String str1;
    }

}