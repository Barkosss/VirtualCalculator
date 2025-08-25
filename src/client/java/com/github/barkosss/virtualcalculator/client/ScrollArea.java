package com.github.barkosss.virtualcalculator.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class ScrollArea {
    private final int x, y, width, height;
    private final Font font;
    private List<String> content = List.of("");
    private int yOffset = 0;

    public ScrollArea(int x, int y, int width, int height, Font font) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public String getText() {
        return String.join("\n", content);
    }

    public void render(GuiGraphics graphics) {
        int lines = 0;
        for (String line : content) {
            if (y + lines * 9 - yOffset >= y && y + lines * 9 - yOffset < y + height) {
                graphics.drawString(font, line, x + 2, y + lines * 9 - yOffset, 0xDDDDDD, false);
            }
            lines++;
        }

        // Scroll
        if (content.size() * 9 > height) {
            float ratio = (float) height / (content.size() * 9);
            int barHeight = (int) (height * ratio);
            int barY  = y + (int) (yOffset / (float)(content.size() * 9 - height) * (height - barHeight));
            graphics.fill(x + width - 4, barY, x + width - 2, barY + barHeight, 0x80FFFFFF);
        }
    }
}
