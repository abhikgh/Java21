package com.src.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldTest {

    public String pub;
    protected String pro;
    private String pri;

    private void somePrivateMethod() {
    }

    private void somePrivateMethod2(String string) {
        System.out.println(string.toUpperCase());
    }

    @JsonIgnore
    private void testAnnotationPresent() {
        System.out.println("yes");
    }

    class InnerFieldTest {
        private String str1;
    }

}