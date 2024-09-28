package com.src.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReflectionTest {

    public String pub;
    protected String pro;
    private String pri;

    private void somePrivateMethod() {
    }

    private void somePrivateMethod2(String string) {
        System.out.println(string.toUpperCase());
    }

    private String privateMethodConcat(String input1, String input2) {
        String output = input1.toUpperCase().concat(input2.toUpperCase());
        System.out.println(output);
        return output;
    }

    @JsonIgnore
    private void testAnnotationPresent() {
        System.out.println("yes");
    }

    class InnerFieldTest {
        private String str1;
    }

}