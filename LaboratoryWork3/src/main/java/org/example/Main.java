package org.example;

import org.example.filter.SimpleLogFilter;
import org.example.handler.ConsoleHandler;
import org.example.handler.FileHandler;
import org.example.handler.SocketHandler;
import org.example.handler.SyslogHandler;
import org.example.servers.SocketServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.handle("Hello World");

        FileHandler fileHandler = new FileHandler("log.txt");
        fileHandler.handle("java: cannot find symbol\n" +
                "  symbol:   method handl(java.lang.String)\n" +
                "  location: variable fileHandler of type org.example.handler.FileHandler");

        SimpleLogFilter exceptionSimpleLogFilter = new SimpleLogFilter("Exception");
        String log = "Ou no! There is exception!";
        String log2 = "Ou no! There is error!";

        if (exceptionSimpleLogFilter.match(log)){
            consoleHandler.handle(log);
        }

        if (exceptionSimpleLogFilter.match(log2)){
            consoleHandler.handle(log2);
        }
        SocketServer server = new SocketServer();
        SocketHandler socketHandler = new SocketHandler();

        socketHandler.handle("auuuuu");
        server.listen();
        server.close();
        socketHandler.close();

        SyslogHandler syslogHandler = new SyslogHandler();
        syslogHandler.handle("Hello World");

    }
}