package org.practice.model;

import org.practice.memento.ShortcutMemento;

import java.util.HashMap;
import java.util.Map;

public class ShortcutRegistry {
    private final Map<String, String> shortcutToCommand = new HashMap<>();

    public Map<String, String> getAllShortcuts() {return new HashMap<>(shortcutToCommand);}

    public void assignShortcut(String shortcut, String command) {
        shortcutToCommand.entrySet().removeIf(e -> e.getValue().equals(command));
        shortcutToCommand.put(shortcut, command);
    }

    public void restoreFromMemento(ShortcutMemento shortcutMemento) {
        shortcutToCommand.clear();
        shortcutToCommand.putAll(shortcutMemento.getSnapshot());
    }
}
