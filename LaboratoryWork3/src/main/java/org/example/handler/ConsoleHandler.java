package org.example.handler;

public class ConsoleHandler implements ILogHandler{

    @Override
    public void handle(String text) {
        if (text != null) {
            System.out.println(text);
        }
        else {
            throw new NullPointerException();
        }
    }
}
