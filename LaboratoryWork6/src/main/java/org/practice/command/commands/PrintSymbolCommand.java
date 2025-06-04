package org.practice.command.commands;

import org.practice.model.Symbol;

public class PrintSymbolCommand implements UndoableCommand{

    private final Symbol symbol;

    public PrintSymbolCommand(Symbol symbol) {this.symbol = symbol;}

    @Override
    public void undo() {
        symbol.delete();
    }

    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public void execute() {
        symbol.print();
    }
}
