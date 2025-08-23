package barkos.virtualcalculator.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

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

            this.addRenderableWidget(Button.builder(
                    Component.literal(label),
                    btn -> onButtonClicked(label)
            ).pos(x, y).size(28, 20).build());
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifies) {
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            onButtonClicked("=");
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            onButtonClicked("Del");
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_C) {
            onButtonClicked("Clear");
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifies);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        String allowedChars = "0123456789.=/*-+()";
        if (allowedChars.indexOf(chr) != -1) {
            onButtonClicked(String.valueOf(chr));
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    private void onButtonClicked(String label) {
        switch (label.toLowerCase()) {
            case "=" -> {
                try {
                    displayText = evaluateExpression(displayText);
                } catch (Exception ex) {
                    System.out.println("onButtonClicked failed: " + ex.getMessage());
                    displayText = "Error";
                }
            }

            case "clear" -> displayText = "0";
            case "del" -> {
                if (displayText.isEmpty() || displayText.equals("Error")) {
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
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);

        int startX = this.width / 2 - 60;
        int startY = this.height / 2 - 70;

        graphics.fill(startX, startY - 20, startX + 120, startY, 0xFF2B2B2B);

        String display = displayText.length() > 16 ? "..." + displayText.substring(Math.max(0, displayText.length() - 13)) : displayText;

        graphics.drawString(
                this.font,
                display,
                startX + 4,
                startY - 15,
                0xFFFFFF,
                false
        );

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Game not pause
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }

    // TODO: Rewrite
    private String evaluateExpression(String expression) {
        try {
            Double result = CalculatorLogic.execute(expression);

            if (result == null) return "Error";
            if (result == Math.round(result)) {
                return String.valueOf(result.intValue());
            } else {
                return String.format("%.6f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            }
        } catch (Exception ex) {
            System.out.println("Evaluation failed: " + ex.getMessage());
            return "Error";
        }
    }
}
