package org.practice.model;

import java.util.HashMap;
import java.util.Map;

public class ShortcutRegistry {
    private final Map<String, String> shortcutToCommand = new HashMap<>();

    public Map<String, String> getAllShortcuts() {return new HashMap<>(shortcutToCommand);}

    public void assignShortcut(String shortcut, String command) {
        shortcutToCommand.put(shortcut, command);
    }
}
