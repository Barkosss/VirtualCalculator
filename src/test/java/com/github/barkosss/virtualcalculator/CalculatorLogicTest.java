package com.github.barkosss.virtualcalculator;

import com.github.barkosss.virtualcalculator.client.CalculatorLogic;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Objects;

public class CalculatorLogicTest {

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
    void eleventhTestPositive() {
        String expression = "(((((((5 * (-3))))))))";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-15)); // true
    }

    @Test
    void twelfthTestPositive() {
        String expression = "(5 * 3 + ( -5 * 5 ) * 7 + 5)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null; // true
        assert Objects.equals(result, new BigDecimal(-155)); // true
    }

    @Test
    void thirteenthTestNegative() {
        String expression = "(5 + 5) * 2s";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fourteenthTestNegative() {
        String expression = "(5 + 5) * 2 +";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void fifteenthTestNegative() {
        String expression = "() 5 + 5";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void sixteenthTestNegative() {
        String expression = "(1 + 1 * 5 + 5 + 2 * 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void seventeenthTestNegative() {
        String expression = "(1 + 1 * 5( + 5) +) 2 * 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void eighteenthTestNegative() {
        String expression = "(1 + 1 * (5 + 5) +) 2 * 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null; // true
    }

    @Test
    void nineteenthTestPositive() {
        String expression = "484528044530-10";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal(484528044520L));
    }

    @Test
    void twentiethTestPositive() {
        String expression = "999999999999999999999999 + 1";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("1000000000000000000000000"));
    }

    @Test
    void twentiethOneTestPositive() {
        String expression = "0.0000001 * 0.0000001";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("0.00000000000001"));
    }

    @Test
    void twentiethTwoTestPositive() {
        String expression = "1 / 3";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert result.toPlainString().startsWith("0.3333");
    }

    @Test
    void twentiethThreeTestNegative() {
        String expression = "5 / 0";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null;
    }

    @Test
    void twentiethFourTestPositive() {
        String expression = "(-5) * (-3)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("15"));
    }

    @Test
    void twentiethFiveTestNegative() {
        String expression = "   ";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, BigDecimal.ZERO);
    }

    @Test
    void twentiethSixTestPositive() {
        String expression = "2 + 3 * 4 - 6 / 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert result.compareTo(new BigDecimal("11")) == 0;
    }

    @Test
    void twentiethSevenTestPositive() {
        String expression = "(-2) * (3 + -4)";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("2"));
    }

    @Test
    void twentiethEightTestPositive() {
        String expression = "1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("55"));
    }

    @Test
    void twentiethNineTestPositive() {
        String expression = "0.1 + 0.02 + 0.003";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("0.123"));
    }

    @Test
    void thirtiethTestPositive() {
        String expression = "((((((((((1+1))))))))))";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("2"));
    }

    @Test
    void thirtiethOneTestPositive() {
        String expression = "10 - 2 * (3 + 1) / 2";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert result.compareTo(new BigDecimal("6")) == 0;
    }

    @Test
    void thirtiethTwoTestPositive() {
        String expression = "   5    +    3   ";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result != null;
        assert Objects.equals(result, new BigDecimal("8"));
    }

    @Test
    void thirtiethThreeTestNegative() {
        String expression = "--5 + ---3";
        BigDecimal result = CalculatorLogic.execute(expression);

        assert result == null;
    }

}
