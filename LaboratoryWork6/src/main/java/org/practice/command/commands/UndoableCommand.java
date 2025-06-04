package org.practice.command.commands;

public interface UndoableCommand extends Command {
    void undo();
    void redo();
}
