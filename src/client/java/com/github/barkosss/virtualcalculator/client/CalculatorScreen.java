package com.github.barkosss.virtualcalculator.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CalculatorScreen extends Screen {
    private enum Mode {
        CALCULATOR,
        NOTE_EDIT
    }

    private Mode currentMode = Mode.CALCULATOR;

    private String displayText = "0";
    private final List<String> buttons = Arrays.asList(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "Clear", "(", ")", "Del"
    );

    private static final int PANEL_WIDTH = 80;
    private ScrollArea noteScrollArea, historyScrollArea;
    private EditBox noteInput;


    public CalculatorScreen() {
        super(Component.literal("Calculator"));
        CalculatorData.getInstance().load();
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        int centerX = this.width / 2;
        int startY = this.height / 2 - 70;

        // --- Left panel: Notebook ---
        int leftX = 10;
        int rightX = this.width - PANEL_WIDTH - 10;

        // Scroll-square for note
        noteScrollArea = new ScrollArea(leftX, startY, PANEL_WIDTH, 150, font);

        noteInput = new EditBox(
                this.font,
                10,
                startY,
                PANEL_WIDTH,
                150,
                Component.literal("Note")
        );

        noteInput.setMaxLength(500);
        noteInput.setValue(CalculatorData.getInstance().getCurrentNote());
        noteInput.setBordered(true);
        noteInput.setResponder(text -> CalculatorData.getInstance().setCurrentNote(text));
        this.addRenderableWidget(noteInput);

        Button modeButton = Button.builder(getModeLabel(), btn -> {
            if (currentMode == Mode.CALCULATOR) {
                currentMode = Mode.NOTE_EDIT;
                this.setFocused(noteInput);
            } else {
                currentMode = Mode.CALCULATOR;
                CalculatorData.getInstance().setCurrentNote(noteInput.getValue());
                CalculatorData.getInstance().save();
                this.setFocused(null);
            }
            btn.setMessage(getModeLabel());
        }).pos(leftX, startY - 20).size(100, 12).build();

        this.addRenderableWidget(modeButton);

        // --- Center: buttons of calc ---
        int calcStartX = centerX - 60;
        int buttonSize = 30;
        int buttonSpacing = 6;
        for (int index = 0; index < buttons.size(); index++) {
            int x = calcStartX + (index % 4) * (buttonSize + buttonSpacing);
            int y = startY + (index / 4) * (buttonSize + buttonSpacing);
            String label = buttons.get(index);

            this.addRenderableWidget(Button.builder(
                    Component.literal(label),
                    btn -> {
                        onButtonClicked(label);
                        this.setFocused(null);
                    }
            ).pos(x, y).size(buttonSize, buttonSize).build());
        }

        // --- Right panel: History ---
        historyScrollArea = new ScrollArea(rightX, startY, PANEL_WIDTH, 150, font);
    }

    private Component getModeLabel() {
        return Component.literal("Mode: " + (currentMode == Mode.CALCULATOR ? "Calculator" : "Notes"));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (currentMode == Mode.CALCULATOR) {
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
        } else if (currentMode == Mode.NOTE_EDIT) {
            if (noteInput.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (currentMode == Mode.CALCULATOR) {
            String allowedChars = "0123456789.=/*-+()";
            if (allowedChars.indexOf(chr) != -1) {
                onButtonClicked(String.valueOf(chr));
                return true;
            }
        } else if (currentMode == Mode.NOTE_EDIT) {
            return noteInput.charTyped(chr, modifiers);
        }
        return super.charTyped(chr, modifiers);
    }

    private void onButtonClicked(String label) {
        switch (label.toLowerCase()) {
            case "=" -> {
                try {
                    String expression = displayText;
                    String result = evaluateExpression(displayText);
                    displayText = result;

                    CalculatorData.getInstance().addHistoryEntry(expression, result);
                } catch (Exception ex) {
                    System.out.println("onButtonClicked failed: " + ex.getMessage());
                    displayText = "Error";
                }
            }

            case "clear" -> displayText = "";
            case "del" -> {
                if (displayText.isEmpty() || displayText.equals("Error")) {
                    displayText = "";
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
        this.setFocused(false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);

        super.render(graphics, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int startY = this.height / 2 - 70;

        // --- Input Field ---
        graphics.fill(centerX - 60, startY - 20, centerX + 80, startY - 5, 0xFF2B2B2B);
        graphics.drawString(font, displayText, centerX - 58, startY - 15, 0xFFFFFF, false);

        // --- Left panel: Notes ---
        int leftX = 10;
        int rightX = this.width - PANEL_WIDTH - 10;

        graphics.fill(leftX, startY, leftX + PANEL_WIDTH, startY + 150, 0xFF1E1E1E);
        graphics.drawString(font, "Notes", leftX + 2, startY - 10, 0xAAAAAA, false);
        noteInput.render(graphics, mouseX, mouseY, delta);

        noteScrollArea.setYOffset(noteScrollY);
        noteScrollArea.render(graphics);

        // --- Right panel: History ---
        graphics.fill(rightX, startY, rightX + PANEL_WIDTH, startY + 150, 0xFF1E1E1E);
        graphics.drawString(font, "History", rightX + 2, startY - 10, 0xAAAAAA, false);

        this.addRenderableWidget(Button.builder(
                Component.literal("Clear"),
                btn -> {
                    CalculatorData.getInstance().clearHistory();
                    this.init();
                }
        ).pos(rightX + PANEL_WIDTH - 40, startY - 15).size(40, 12).build());
        historyScrollArea.setContent(getHistoryLines());
        historyScrollArea.setYOffset(historyScrollY);
        historyScrollArea.render(graphics);
    }

    private List<String> getHistoryLines() {
        return CalculatorData.getInstance().getHistory();
    }

    // --- Control scroll ---
    private int noteScrollY = 0;
    private int historyScrollY = 0;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (noteScrollArea.isMouseOver(mouseX, mouseY)) {
            noteScrollY = Math.max(0, noteScrollY - (int) (deltaY * 10));
            return true;
        }
        if (historyScrollArea.isMouseOver(mouseX, mouseY)) {
            historyScrollY = Math.max(0, historyScrollY - (int) (deltaY * 10));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Game not pause
    }

    @Override
    public void onClose() {
        CalculatorData.getInstance().setCurrentNote(noteInput.getValue());
        CalculatorData.getInstance().save();
        Minecraft.getInstance().setScreen(null);
        super.onClose();
    }

    private String evaluateExpression(String expression) {
        try {
            BigDecimal result = CalculatorLogic.execute(expression);

            if (result == null) return "Error";
            if (result.intValue() == Math.round(result.doubleValue())) {
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
