package barkos.virtualcalculator.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;
import java.util.List;

public class CalculatorScreen extends Screen {
    private String displayText = "0";
    private final List<String> buttons = Arrays.asList(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "Clear", "(", ")", "Del"
    );

    CalculatorScreen() {
        super(Component.literal("Calculator"));
    }

    @Override
    public void init() {
        super.init();

        int startX = this.width / 2 - 50;
        int startY = this.height / 2 - 50;

        for (int i = 0; i < buttons.size(); i++) {
            int x = startX + (i % 4) * 30;
            int y = startY + (i / 4) * 30;
            String label = buttons.get(i);


        }
    }

    private void onButtonClicked(String label) {
        switch (label.toLowerCase()) {
            case "=" -> {
                try {
                    displayText = evaluateExpression(displayText);
                } catch (Exception ex) {
                    displayText = "Error";
                }
            }

            case "clear" -> displayText = "0";
            case "del" -> {
                if (displayText.isEmpty()) {
                    displayText = "0";
                } else {
                    displayText = displayText.substring(0, displayText.length() - 1);
                }
            }
            default -> {
                if (displayText.equals("0") && Character.isDigit(label.charAt(0))) {
                    displayText = label;
                } else if (displayText.equals("Error")) {
                    displayText = label;
                } else {
                    displayText += label;
                }
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public boolean shouldPause() {
        return false; // Game not pause
    }

    private String evaluateExpression(String expression) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval(expression);
            return result.toString();
        } catch (Exception ex) {
            return "Error";
        }
    }
}
