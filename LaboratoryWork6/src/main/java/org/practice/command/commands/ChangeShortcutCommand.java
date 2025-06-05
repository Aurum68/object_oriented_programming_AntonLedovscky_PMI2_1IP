package org.practice.command.commands;

import lombok.RequiredArgsConstructor;
import org.practice.controller.ShortcutChangeListener;
import org.practice.memento.ShortcutMemento;
import org.practice.model.ShortcutRegistry;
import org.practice.persistence.ShortcutRegistryPersistence;

@RequiredArgsConstructor
public class ChangeShortcutCommand implements UndoableCommand {

    private final ShortcutChangeListener listener;
    private final ShortcutRegistry shortcutRegistry;
    private ShortcutMemento beforeMemento;
    private ShortcutMemento afterMemento;

    @Override
    public void execute() {
        new Thread(() -> {
            String answer = this.listener.onChangeShortcutRequested();

            String shortcut = answer.split(":")[0];
            String command = answer.split(":")[1];

            beforeMemento = new ShortcutMemento(shortcutRegistry);
            shortcutRegistry.assignShortcut(shortcut, command);
            afterMemento = new ShortcutMemento(shortcutRegistry);

            ShortcutRegistryPersistence.save(afterMemento);
            listener.updateCommands();
        }, "Shortcut-Registration-Thread").start();
    }

    @Override
    public void undo() {
        shortcutRegistry.restoreFromMemento(beforeMemento);
        ShortcutRegistryPersistence.save(beforeMemento);
    }

    @Override
    public void redo() {
        shortcutRegistry.restoreFromMemento(afterMemento);
        ShortcutRegistryPersistence.save(afterMemento);
    }
}
