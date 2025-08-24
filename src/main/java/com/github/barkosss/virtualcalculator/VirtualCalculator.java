package com.github.barkosss.virtualcalculator;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtualCalculator implements ModInitializer {
    public static final String MOD_ID = "VirtualCalculator";
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        logger.info("Mod {} is load!", MOD_ID);
    }
}
