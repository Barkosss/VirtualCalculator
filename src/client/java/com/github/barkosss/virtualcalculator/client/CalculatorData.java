package com.github.barkosss.virtualcalculator.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorData {
    private List<String> history = new ArrayList<>();

    private static final CalculatorData instance = new CalculatorData();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static CalculatorData getInstance() {
        return instance;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public void addHistoryEntry(String expression, String result) {
        history.add(expression);
        history.add(" ".repeat(6) + "=" + result);
    }

    public void clearHistory() {
        history.clear();
    }

    public void save() {
        Path path = Minecraft.getInstance().gameDirectory.toPath().resolve("config/virtualcalculator/data.json");
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = new FileWriter(path.toFile())) {
                gson.toJson(this, writer);
            }
        } catch (IOException ex) {
            Logger.getLogger(CalculatorData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        Path path = Minecraft.getInstance().gameDirectory.toPath().resolve("config/virtualcalculator/data.json");
        if (Files.exists(path)) {
            try (Reader reader = new FileReader(path.toFile())) {
                CalculatorData data = gson.fromJson(reader, CalculatorData.class);
                this.history = data.history;
            } catch (IOException ex) {
                Logger.getLogger(CalculatorData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
