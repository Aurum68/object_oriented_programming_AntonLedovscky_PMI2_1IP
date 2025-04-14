package org.example;

import org.example.filter.ILogFilter;
import org.example.filter.ReLogFilter;
import org.example.filter.SimpleLogFilter;
import org.example.handler.*;
import org.example.servers.SocketServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SocketServer server = new SocketServer();


        ILogFilter simpleLogFilter = new SimpleLogFilter("error");
        ILogFilter reLogFilter = new ReLogFilter("^.*error.*$");

        ILogHandler consoleHandler = new ConsoleHandler();
        ILogHandler fileHandler = new FileHandler("error.txt");
        ILogHandler socketHandler = new SocketHandler();
        ILogHandler syslogHandler = new SyslogHandler();

        String log = "Error! Line 10000000000";

        Logger logger = new Logger(new ILogFilter[]{simpleLogFilter, reLogFilter},
                new ILogHandler[]{consoleHandler, fileHandler, socketHandler, syslogHandler});

        boolean results = logger.log(log);
        if (!results) return ;
        server.listen();
        server.close();
    }
}