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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorData {
    private Map<String, String> notes = new HashMap<>();
    private List<String> history = new ArrayList<>();

    private String currentNoteId = "note_1";
    private static final CalculatorData instance = new CalculatorData();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static CalculatorData getInstance() {
        return instance;
    }

    public String getCurrentNote() {
        return notes.computeIfAbsent(this.currentNoteId, key -> "");
    }

    public void setCurrentNote(String text) {
        notes.put(this.currentNoteId, text);
    }

    public String getCurrentNoteId() {
        return this.currentNoteId;
    }

    public void setCurrentNoteId(String id) {
        this.currentNoteId = id;
    }

    public Set<String> getNoteIds() {
        return notes.keySet();
    }

    public void createNewNote() {
        String newId;
        int index = 1;
        do {
            newId = "note_" + index++;
        } while(notes.containsKey(newId));

        notes.put(newId, "");
        currentNoteId = newId;
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
                this.notes = data.notes;
                this.history = data.history;
                this.currentNoteId = data.currentNoteId != null ? data.currentNoteId : "note_1";
            } catch (IOException ex) {
                Logger.getLogger(CalculatorData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (notes.isEmpty()) {
            createNewNote();
        }
    }
}
