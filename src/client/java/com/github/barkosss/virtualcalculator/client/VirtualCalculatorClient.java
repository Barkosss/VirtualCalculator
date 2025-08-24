package com.github.barkosss.virtualcalculator.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class VirtualCalculatorClient implements ClientModInitializer {
    private static final String KEY_CATEGORY = "key.categories.calculator";
    private static final String KEY_OPEN = "key.calculator.open";

    public static KeyMapping OPEN_CALCULATOR;

    @Override
    public void onInitializeClient() {
        OPEN_CALCULATOR = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_OPEN,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY
        ));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            while (OPEN_CALCULATOR.consumeClick()) {
                if (client.player != null && client.screen == null) {
                    client.setScreen(new CalculatorScreen());
                }
            }
        });
    }
}
