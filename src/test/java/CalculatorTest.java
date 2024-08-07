import com.src.stream.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.Assert;

import java.util.stream.Stream;

public class CalculatorTest {

    private Calculator calculator;

    public static Stream<Arguments> getListInt() {
        return Stream.of(
          Arguments.of(4, 2, 2),
          Arguments.of(15, 3, 5),
          Arguments.of(484, 4, 121)
        );
    }


    @BeforeEach
    public void setUp(){
        calculator = new Calculator();
    }

    @Test
    @Order(1)
    @DisplayName("Test Calculator multiplication")
    public void testMultiply(){
        Assertions.assertEquals(20, calculator.multiply(4,5));
    }

    @RepeatedTest(5)
    @Order(2)
    @DisplayName("Ensure correct handling of zero")
    void testMultiplyWithZero() {
        Assertions.assertEquals(0, calculator.multiply(0, 5));
        Assertions.assertEquals(0, calculator.multiply(0, 5), "Multiplication with 0 should be 0");
    }

    @Test
    @Order(3)
    @DisplayName("Assert all test")
    public void testAssertAll(){
        Assertions.assertAll("test assert all",
                () -> Assertions.assertEquals(6, calculator.multiply(2,3)),
                () -> Assertions.assertEquals(60, calculator.multiply(6,10)),
                () -> Assertions.assertEquals(120, calculator.multiply(12,10)));
    }

    @ParameterizedTest(name = "{index}")
    @DisplayName("Parameterized test integer for multiplication")
    @MethodSource(value = "getListInt")
    @Order(4)
    public void testParameterizedTypeInteger(int expected, int m1, int m2){
        Assertions.assertEquals(expected, calculator.multiply(m1, m2));
    }

    @ParameterizedTest(name = "{index}==>{0}")
    @DisplayName("Checking csv source")
    @CsvSource({"4, 2, 2", "100, 10, 10", "90, 9, 10", "120, 6, 20"})
    @Order(5)
    public void testCsvSource(@AggregateWith(CalculatorTestDataAggregator.class) CalculatorTestData calculatorTestData) {
        int actualResponse = calculator.multiply(calculatorTestData.getM1(), calculatorTestData.getM2());
        Assertions.assertEquals(calculatorTestData.getExpected(), actualResponse);
    }

    @ParameterizedTest(name = "{index}==>{0}")
    @DisplayName("Checking checkUsingInput")
    @CsvSource({"123, 123", "10, 100", "1000, 100", "400, 100"})
    @Order(6)
    public void testCheckUsingInput(@AggregateWith(TestDataAggregator.class) TestData testData){
        int actualResponse = calculator.checkUsingInput(testData.getInput());
        Assert.assertEquals(testData.getOutput(),actualResponse);
    }



}
