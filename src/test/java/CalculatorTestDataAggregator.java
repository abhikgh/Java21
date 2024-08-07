import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class CalculatorTestDataAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {

        CalculatorTestData calculatorTestData = new CalculatorTestData();
        calculatorTestData.setExpected(argumentsAccessor.getInteger(0));
        calculatorTestData.setM1(argumentsAccessor.getInteger(1));
        calculatorTestData.setM2(argumentsAccessor.getInteger(2));
        return calculatorTestData;

    }
}
