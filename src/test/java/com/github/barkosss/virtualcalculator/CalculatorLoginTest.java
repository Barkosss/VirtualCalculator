package com.github.barkosss.virtualcalculator;

import com.github.barkosss.virtualcalculator.client.CalculatorLogic;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class CalculatorLoginTest {

    @Test
    void addTwoNumberPositive() {
        String expression = "2 + 2";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, 4.0); // true
    }

    @Test
    void diffTwoNumberPositive() {
        String expression = "-1 - 1";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -2.0); // true
    }

    @Test
    void multiTwoNumberPositive() {
        String expression = "-2 * 5";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -10.0); // true
    }

    @Test
    void firstIncorrectBracketsNegative() {
        String expression = "((-1 * 5)";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void secondIncorrectBracketsNegative() {
        String expression = ")(";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void thirdIncorrectBracketsNegative() {
        String expression = "(() -5 * 2)";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test // TODO: Negative
    void fourthCorrectBracketsPositive() {
        String expression = "((-1) * -5 * 2)";
        Double result = CalculatorLogic.execute(expression); // <-- For input string: "("

        assert result != null; // true
        assert Objects.equals(result, 10.0); // true
    }
}
