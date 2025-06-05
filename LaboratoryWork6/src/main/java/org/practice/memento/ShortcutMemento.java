package org.practice.memento;

import org.practice.model.ShortcutRegistry;

import java.util.HashMap;
import java.util.Map;

public class ShortcutMemento {
    private final Map<String, String> snapshot;

    public ShortcutMemento(ShortcutRegistry shortcutRegistry) {
        this.snapshot = new HashMap<>(shortcutRegistry.getAllShortcuts());
    }

    public Map<String, String> getSnapshot() {
        return new HashMap<>(this.snapshot);
    }
}
