package org.practice.command;

import org.practice.command.commands.Command;
import org.practice.command.commands.PrintSymbolCommand;
import org.practice.model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String shortcut, Command command) {
        commands.put(shortcut, command);
    }

    public void unregisterCommand(String shortcut) {
        commands.remove(shortcut);
    }

    public Command getCommand(String shortcut) {
        return commands.get(shortcut);
    }

    public void onShortcutPressed(String shortcut, CommandHistory history) {
        Command command = getCommand(shortcut);
        if (command != null) {
            command.execute();
            history.push(command);
        }
        else if (shortcut.length() == 1 && !Character.isISOControl(shortcut.charAt(0))) {
            Command printCommand = new PrintSymbolCommand(new Symbol(shortcut));
            printCommand.execute();
            history.push(printCommand);
        }
    }

    public Map<String, Command> getAllCommands() {
        return new HashMap<>(commands);
    }

    public void clearCommands() {
        commands.clear();
    }
}
