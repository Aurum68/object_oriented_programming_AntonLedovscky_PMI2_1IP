package org.practice.command;

import org.practice.command.commands.Command;
import org.practice.command.commands.UndoableCommand;

import java.util.Stack;

public class CommandHistory {
    private final Stack<UndoableCommand> undoStack = new Stack<>();
    private final Stack<UndoableCommand> redoStack = new Stack<>();

    public void push(Command command) {
        if (command instanceof UndoableCommand) {
            undoStack.push((UndoableCommand) command);
            redoStack.clear();
        }
    }

    public void undo(){
        if(undoStack.isEmpty()) return;
        UndoableCommand command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo(){
        if(redoStack.isEmpty()) return;
        UndoableCommand command = redoStack.pop();
        command.redo();
        undoStack.push(command);
    }
}
