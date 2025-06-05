package org.practice.persistence;

import org.json.simple.JSONObject;
import org.practice.memento.ShortcutMemento;
import org.practice.model.ShortcutRegistry;
import org.practice.writers.JsonWriter;

import java.util.HashMap;

public class ShortcutRegistryPersistence {

    private static final JsonWriter JSON_WRITER = new JsonWriter();

    public static void save(ShortcutMemento shortcutMemento) {
        JSON_WRITER.write(new HashMap<>(shortcutMemento.getSnapshot()));
    }

    public static ShortcutRegistry load() {
        JSONObject jsonObject = JSON_WRITER.getJsonObject();

        if (jsonObject == null) throw new RuntimeException("JSON object is null");
        if (jsonObject.isEmpty()) throw new RuntimeException("JSON object is empty");

        ShortcutRegistry shortcutRegistry = new ShortcutRegistry();
        for (Object key : jsonObject.keySet()) {
            shortcutRegistry.assignShortcut((String) key, (String) jsonObject.get(key));
        }
        return shortcutRegistry;
    }
}
