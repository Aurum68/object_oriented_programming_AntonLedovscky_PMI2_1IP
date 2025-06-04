package org.practice.command.commands;

import lombok.RequiredArgsConstructor;
import org.practice.command.CommandHistory;

@RequiredArgsConstructor
public class RedoCommand implements Command {
    private final CommandHistory commandHistory;

    @Override
    public void execute() {
        commandHistory.redo();
    }
}
