package org.practice.model;

import org.practice.writers.TxtWriter;

public class Symbol {

    private static final TxtWriter TXT_WRITER = new TxtWriter();

    private String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
    }

    public void print() {
        TXT_WRITER.write(this.symbol, true);
    }

    public void delete(){
        TXT_WRITER.deleteLastSymbolFromFirstLine();
    }
}
