package com.github.barkosss.virtualcalculator.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.math.BigDecimal;

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

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("calc")
                .then(ClientCommandManager.argument("expression", StringArgumentType.greedyString())
                        .executes(context -> {
                            String expression = context.getArgument("expression", String.class);

                            BigDecimal answer = CalculatorLogic.execute(expression);

                            String result;
                            if (answer == null) {
                                result = "Error";
                            } else if (answer.intValue() == Math.round(answer.doubleValue())) {
                                result = String.valueOf(answer.intValue());
                            } else {
                                result = String.format("%.6f", answer).replaceAll("0*$", "").replaceAll("\\.$", "");
                            }

                            Minecraft client = Minecraft.getInstance();
                            if (client.player != null) {
                                client.player.displayClientMessage(
                                        Component.literal("Result: " + result), false
                                );
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                )
        ));
    }
}
