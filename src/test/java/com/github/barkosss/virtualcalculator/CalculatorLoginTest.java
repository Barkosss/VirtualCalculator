package com.github.barkosss.virtualcalculator;

import com.github.barkosss.virtualcalculator.client.CalculatorLogic;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

public class CalculatorLoginTest {

    @Test
    void addTwoNumberPositive() {
        String expression = "2 + 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(4)); // true
    }

    @Test
    void diffTwoNumberPositive() {
        String expression = "-1 - 1";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-2)); // true
    }

    @Test
    void multiTwoNumberPositive() {
        String expression = "-2 * 5";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-10)); // true
    }

    @Test
    void firstIncorrectBracketsNegative() {
        String expression = "((-1 * 5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void secondIncorrectBracketsNegative() {
        String expression = ")(";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void thirdIncorrectBracketsNegative() {
        String expression = "(() -5 * 2)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fourthCorrectBracketsPositive() {
        String expression = "((-1) * -5 * 2)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(10)); // true
    }

    @Test
    void firstTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-155)); // true
    }

    @Test
    void secondTestNegative() {
        String expression = "5 * -3 / 4 * -7)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void thirdTestNegative() {
        String expression = "(5 * 3( * )7 +5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fourthTestNegative() {
        String expression = "() 5 + 5";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fifthTestNegative() {
        String expression = "(5 + 5) * 2s";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void sixthTestNegative() {
        String expression = "(5 + 5) * 2 +";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void seventhTestPositive() {
        String expression = "-5 * 3";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-15)); // true
    }

    @Test
    void eighthTestPositive() {
        String expression = "(5 + 2 - 1) * (5 + 2)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(42)); // true
    }

    @Test
    void ninthTestNegative() {
        String expression = "(5 * 3( * )7 +5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void tenthTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-155)); // true
    }

    @Test
    void twelfthTestPositive() {
        String expression = "(((((((5 * (-3))))))))";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-15)); // true
    }

    @Test
    void twentiethTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-155)); // true
    }

    @Test
    void thirtiethTestNegative() {
        String expression = "(5 + 5) * 2s";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fortiethTestNegative() {
        String expression = "(5 + 5) * 2 +";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fiftiethTestNegative() {
        String expression = "() 5 + 5";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void sixtiethTestNegative() {
        String expression = "(1 + 1 * 5 + 5 + 2 * 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void seventiethTestNegative() {
        String expression = "(1 + 1 * 5( + 5) +) 2 * 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void eightiethTestNegative() {
        String expression = "(1 + 1 * (5 + 5) +) 2 * 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void ninetiethTestPositive() {
        String expression = "484528044530-10";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        // 484528044530 * -1 => -484528044520
        System.out.println(result);
        assert Objects.equals(result, new BigDecimal(484528044520L));
    }
}
