package org.practice.command.commands;

import lombok.RequiredArgsConstructor;
import org.practice.controller.ShortcutChangeListener;
import org.practice.model.ShortcutRegistry;
import org.practice.persistence.ShortcutRegistryPersistence;

@RequiredArgsConstructor
public class ChangeShortcutCommand implements Command {

    private final ShortcutChangeListener listener;
    private final ShortcutRegistry shortcutRegistry;

    @Override
    public void execute() {
        new Thread(() -> {
            String answer = this.listener.onChangeShortcutRequested();

            String shortcut = answer.split(":")[0];
            String command = answer.split(":")[1];

            shortcutRegistry.assignShortcut(shortcut, command);

            ShortcutRegistryPersistence.save(shortcutRegistry);
            listener.updateCommands();
        }, "Shortcut-Registration-Thread").start();
    }
}
