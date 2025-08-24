package com.github.barkosss.virtualcalculator.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

// TODO: -1-1 => Error
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

    public static Double execute(String expression) {
        stack.clear();
        heap.clear();

        expression = expression.replaceAll("\\s+", "");
        expression = replaceAll(expression).trim();
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

                    while (!stack.isEmpty() && stack.getLast().equals("(")) {
                        heap.add(stack.pop());
                    }

                    stack.pop();
                    break;
                }

                case "-": {

                    // If negative number
                    if (index == 0 || Character.isDigit((char) Double.parseDouble(expressions.get(index - 1))) && !Character.isDigit((char) Double.parseDouble(expressions.get(index + 1)))) {
                        heap.add(-1 * Double.parseDouble(expressions.get(index + 1)));
                        index++;
                        break;
                    }
                }

                case "+": {
                }
                case "*": {
                }
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
                        heap.add(Double.parseDouble(number));
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

        Stack<Double> integers = new Stack<>();

        while (!heap.isEmpty()) {
            Object element = heap.removeFirst();

            if (element instanceof Double) {
                integers.push((double) element);
                continue;
            }

            switch (String.valueOf(element)) {
                case "+": {
                    integers.push(integers.pop() + integers.pop());
                    break;
                }

                case "-": {
                    integers.push(-1 * integers.pop() + integers.pop());
                    break;
                }

                case "*": {
                    integers.push(integers.pop() * integers.pop());
                    break;
                }

                case "/": {
                    double two = integers.pop();
                    double one = integers.pop();
                    if (two == 0) {
                        return null;
                    }

                    integers.push(one / two);
                    break;
                }

                case "^": {
                    double two = integers.pop();
                    double one = integers.pop();
                    integers.push(Math.pow(one, two));
                    break;
                }
            }
        }

        return integers.getFirst();
    }

    // TODO: Fix
    private static boolean bracketsFilter(List<String> expression) {
        return true;
    }

    // TODO: Fix
    private static boolean correctExpression(List<String> expression) {
//        for (int index = 0; index <  expression.length; index++) {
//            if ("-+*/^".contains(expression.get(index)) && "-+*/^".contains(expression.get(index + 1))) {
//                return false;
//            }
//        }

        return true;
    }

    private static String replaceAll(String input) {
        String result = input;
        for (int i = 0; i < searchList.length; i++) {
            result = result.replace(searchList[i], replacementList[i]);
        }
        return result;
    }
}
