package com.github.barkosss.virtualcalculator.client;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CalculatorLogic {
    private static final Stack<Object> stack = new Stack<>();
    private static final List<Object> heap = new ArrayList<>();

    private static final String[] searchList = {"(", ")", "+", "-", "*", "/", "^", "d", ","};
    private static final String[] replacementList = {" ( ", " ) ", " + ", " - ", " * ", " / ", " ^ ", " d ", "."};

    private static final Map<String, Integer> priority = initMap();

    private static Map<String, Integer> initMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("(", 0);
        map.put(")", 0);
        map.put("+", 1);
        map.put("-", 1);
        map.put("*", 2);
        map.put("/", 2);
        map.put("^", 3);
        return map;
    }

    public static BigDecimal execute(String expression) {
        stack.clear();
        heap.clear();

        expression = expression.replaceAll("\\s+", "");
        expression = replaceAll(expression).trim();

        if (expression.isEmpty()) {
            return BigDecimal.ZERO;
        }

        List<String> expressions = List.of(expression.split("\\s+"));

        if (!bracketsFilter(expressions)) {
            return null;
        }

        if (!correctExpression(expressions)) {
            return null;
        }

        for (int index = 0; index < expressions.size(); index++) {

            switch (expressions.get(index)) {
                case "(": {

                    if (priority.containsKey(expressions.get(index + 1)) && !expressions.get(index + 1).equals("-") && !expressions.get(index + 1).equals("(")) {
                        return null;
                    }

                    stack.add("(");
                    break;
                }

                case ")": {
                    if (stack.search("(") == 0) {
                        return null;
                    }

                    if ((priority.containsKey(expressions.get(index - 1)) && !expressions.get(index - 1).equals("-")) && !expressions.get(index - 1).equals(")")) {
                        return null;
                    }

                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        heap.add(stack.pop());
                    }

                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                    break;
                }

                case "-": {

                    // If negative number
                    if (index == 0 || !Character.isDigit(expressions.get(index - 1).charAt(0)) && Character.isDigit(expressions.get(index + 1).charAt(0))) {
                        // TODO: bug
                        heap.add(new BigDecimal(expressions.get(index + 1)).multiply(new BigDecimal(-1)));
                        index++;
                        break;
                    }
                }

                case "+":
                case "*":
                case "/": {
                    if (stack.isEmpty() || priority.get(expressions.get(index)) > priority.get(stack.getLast())) { // TODO: Fix
                        stack.push(expressions.get(index));
                    } else {
                        while (!stack.isEmpty() && !stack.getLast().equals("(") && priority.get(expressions.get(index)) <= priority.get(stack.getLast())) { // TODO: Fix
                            heap.add(stack.pop());
                        }
                        stack.push(expressions.get(index));
                    }

                    break;
                }

                case "^": {
                    stack.push(expressions.get(index));
                    break;
                }

                default: {
                    String number = expressions.get(index);

                    try {
                        heap.add(new BigDecimal(number));
                    } catch (Exception ex) {
                        return null;
                    }

                    break;
                }
            }
        }

        while (!stack.isEmpty()) {
            heap.add(stack.pop());
        }

        Stack<BigDecimal> integers = new Stack<>();

        while (!heap.isEmpty()) {
            Object element = heap.removeFirst();

            if (element instanceof BigDecimal) {
                integers.push((BigDecimal)  element);
                continue;
            }

            switch (String.valueOf(element)) {
                case "+": {
                    integers.push(integers.pop().add(integers.pop()));
                    break;
                }

                case "-": {
                    BigDecimal one = integers.pop();
                    BigDecimal two = integers.pop();
                    integers.push(two.subtract(one));
                    break;
                }

                case "*": {
                    integers.push(integers.pop().multiply(integers.pop()));
                    break;
                }

                case "/": {
                    BigDecimal two = integers.pop();
                    BigDecimal one = integers.pop();
                    if (two.equals(new BigDecimal(0))) {
                        return null;
                    }

                    integers.push(one.divide(two, 6, MathContext.DECIMAL64.getRoundingMode()));
                    break;
                }

                case "^": {
                    BigDecimal two = integers.pop();
                    BigDecimal one = integers.pop();
                    integers.push(one.pow(two.intValue()));
                    break;
                }
            }
        }

        return integers.getFirst();
    }

    private static boolean bracketsFilter(List<String> expression) {
        int open = 0;
        int close = 0;
        for (String s : expression) {
            if (s.equals(")")) {
                if (open == 0) {
                    return false;
                }
                close++;
            } else if (s.equals("(")) {
                open++;
            }
        }

        return open == close;
    }

    private static boolean correctExpression(List<String> expression) {
        if (expression.isEmpty()) {
            return false;
        }

        int size = expression.size();

        String first = expression.getFirst();
        if ("+*/^".contains(first) || (first.equals("-") && size > 1 && !isNumber(expression.get(1)))) {
            return false;
        }

        String last = expression.getLast();
        if ("-+*/^".contains(last)) {
            return false;
        }

        for (int index = 0; index < size - 1; index++) {
            String curr = expression.get(index);
            String next = expression.get(index + 1);

            if ("-+*/^".contains(curr) && "-+*/^".contains(next)) {
                if (curr.equals("-") && isNumber(next)) {
                    continue;
                }

                if (isOperator(curr) && next.equals("-") && index + 2 < size && isNumber(expression.get(index + 2))) {
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    private static String replaceAll(String input) {
        String result = input;
        for (int i = 0; i < searchList.length; i++) {
            result = result.replace(searchList[i], replacementList[i]);
        }
        return result;
    }

    private static boolean isOperator(String token) {
        return "-+*/^".contains(token);
    }

    private static boolean isNumber(String token) {
        try {
            new BigDecimal(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
