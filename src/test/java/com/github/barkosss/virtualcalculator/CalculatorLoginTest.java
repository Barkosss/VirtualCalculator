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

    @Test
    void fourthCorrectBracketsPositive() {
        String expression = "((-1) * -5 * 2)";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, 10.0); // true
    }

    @Test
    void firstTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -155.0); // true
    }

    @Test
    void secondTestNegative() {
        String expression = "5 * -3 / 4 * -7)";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void thirdTestNegative() {
        String expression = "(5 * 3( * )7 +5)";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fourthTestNegative() {
        String expression = "() 5 + 5";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fifthTestNegative() {
        String expression = "(5 + 5) * 2s";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void sixthTestNegative() {
        String expression = "(5 + 5) * 2 +";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void seventhTestPositive() {
        String expression = "-5 * 3";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -15.0); // true
    }

    @Test
    void eighthTestPositive() {
        String expression = "(5 + 2 - 1) * (5 + 2)";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, 42.0); // true
    }

    @Test
    void ninthTestNegative() {
        String expression = "(5 * 3( * )7 +5)";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void tenthTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -155.0); // true
    }

    @Test
    void twelfthTestPositive() {
        String expression = "(((((((5 * (-3))))))))";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -15.0); // true
    }

    @Test
    void twentiethTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        Double result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, -155.0); // true
    }

    @Test
    void thirtiethTestNegative() {
        String expression = "(5 + 5) * 2s";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fortiethTestNegative() {
        String expression = "(5 + 5) * 2 +";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fiftiethTestNegative() {
        String expression = "() 5 + 5";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void sixtiethTestNegative() {
        String expression = "(1 + 1 * 5 + 5 + 2 * 2";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void seventiethTestNegative() {
        String expression = "(1 + 1 * 5( + 5) +) 2 * 2";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void eightiethTestNegative() {
        String expression = "(1 + 1 * (5 + 5) +) 2 * 2";
        Double result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }
}
