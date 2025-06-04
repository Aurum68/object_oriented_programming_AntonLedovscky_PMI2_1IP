package org.practice.command;

import org.practice.command.commands.*;
import org.practice.controller.ShortcutChangeListener;
import org.practice.model.MediaPlayer;
import org.practice.model.ShortcutRegistry;
import org.practice.model.Volume;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CommandFactory {
    private ShortcutChangeListener listener;
    private ShortcutRegistry shortcutRegistry;
    private CommandHistory commandHistory;

    private Volume volume;
    private MediaPlayer mediaPlayer;

    private final Map<String, Supplier<Command>> registeredCommands = new HashMap<>();

    public CommandFactory(ShortcutChangeListener listener, ShortcutRegistry shortcutRegistry, CommandHistory commandHistory) {
        this.initialize(listener, shortcutRegistry, commandHistory);

        registerCommand("Change Shortcuts", () -> new ChangeShortcutCommand(this.listener, this.shortcutRegistry));
        registerCommand("Volume Up", () -> new VolumeUpCommand(volume));
        registerCommand("Volume Down", () -> new VolumeDownCommand(volume));
        registerCommand("MediaPlayer On", () -> new MediaPlayerOnCommand(mediaPlayer));
        registerCommand("MediaPlayer Off", () -> new MediaPlayerOffCommand(mediaPlayer));
        registerCommand("Undo", () -> new UndoCommand(this.commandHistory));
        registerCommand("Redo", () -> new RedoCommand(this.commandHistory));
    }

    private void initialize(ShortcutChangeListener listener, ShortcutRegistry shortcutRegistry, CommandHistory commandHistory) {
        this.listener = listener;
        this.shortcutRegistry = shortcutRegistry;
        this.commandHistory = commandHistory;

        volume = new Volume();
        mediaPlayer = new MediaPlayer();
    }

    public Command createCommand(String commandName) {
        var constructor = registeredCommands.get(commandName);
        if (constructor == null) throw new IllegalArgumentException("Command " + commandName + " not found");
        return constructor.get();
    }

    public void registerCommand(String commandName, Supplier<Command> commandSupplier) {
        registeredCommands.put(commandName, commandSupplier);
    }

    public Set<String> getAvailableCommands() {
        return registeredCommands.keySet();
    }
}
