package com.github.barkosss.virtualcalculator.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class NoteEditBox extends EditBox {
    private final Font font;
    private final int maxWidth;

    public NoteEditBox(Font font, int x, int y, int width, int height) {
        super(font, x, y, width, height, Component.literal("NoteInput"));
        this.font = font;
        this.maxWidth = width - 6;
        this.setBordered(true);
        this.setMaxLength(5000);
    }

    /** Возвращает текст заметки построчно (с переносами) */
    public List<String> getWrappedText() {
        String full = this.getValue();
        if (full.isEmpty()) return List.of("");

        String[] words = full.split(" ");
        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (String word : words) {
            String testLine = current.isEmpty() ? word : current + " " + word;
            if (font.width(testLine) > maxWidth) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } else {
                current = new StringBuilder(testLine);
            }
        }
        if (!current.isEmpty()) lines.add(current.toString());
        return lines;
    }

    /** При отрисовке используем перенос */
    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(getX(), getY(), getX() + width, getY() + height, 0xFF1E1E1E);

        List<FormattedCharSequence> lines = font.split(Component.literal(this.getValue()), this.width - 4);

        int lineY = this.getY() + 2;
        for (FormattedCharSequence line : lines) {
            graphics.drawString(font, line, this.getX() + 2, lineY, 0xFFFFFF, false);
            lineY += font.lineHeight;
            if (lineY > this.getY() + this.height - font.lineHeight) {
                break;
            }
        }
    }
}
