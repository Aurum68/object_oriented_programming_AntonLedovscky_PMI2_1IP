package org.example;

import org.example.filter.ILogFilter;
import org.example.handler.ILogHandler;
import org.example.servers.SocketServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private final List<ILogFilter> filters = new ArrayList<>();
    private final List<ILogHandler> handlers = new ArrayList<>();

    public Logger(ILogFilter[] filters, ILogHandler[] handlers) {
        add_filters(filters);
        add_handlers(handlers);
    }

    private void add_filters(ILogFilter[] filters) {
        Collections.addAll(this.filters, filters);
    }

    private void add_handlers(ILogHandler[] handlers) {
        Collections.addAll(this.handlers, handlers);
    }

    public void log(String message) {
        for (ILogFilter filter : filters) {
            if (!filter.match(message)) {
                System.err.println( filter.getClass().getSimpleName() + ": " + message  + " not matched");
                return;
            }
        }

        for (ILogHandler handler : handlers) {
            handler.handle(message);
        }
    }
}
