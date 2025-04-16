package org.example;

import org.example.filter.ILogFilter;
import org.example.handler.ILogHandler;

public class Logger {
    private final ILogFilter[] filters;
    private final ILogHandler[] handlers;

    public Logger(ILogFilter[] filters, ILogHandler[] handlers) {
        this.filters = filters;
        this.handlers = handlers;
    }

    public boolean log(String message) {
        for (ILogFilter filter : filters) {
            if (!filter.match(message)) {
                System.err.println( filter.getClass().getSimpleName() + ": " + message  + " not matched");
                return false;
            }
        }

        for (ILogHandler handler : handlers) {
            handler.handle(message);
        }

        return true;
    }
}
