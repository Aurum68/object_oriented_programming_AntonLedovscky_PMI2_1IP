package org.practice.keyboard;

import org.practice.command.CommandManager;
import org.practice.command.CommandHistory;

public class ShortcutActionListener implements ShortcutListener{

    private final CommandManager commandManager;
    private final CommandHistory commandHistory;

    public ShortcutActionListener(CommandManager commandManager, CommandHistory commandHistory) {
        this.commandManager = commandManager;
        this.commandHistory = commandHistory;
    }

    @Override
    public void onShortcutDetected(String shortcut) {
        commandManager.onShortcutPressed(shortcut, commandHistory);
    }
}
