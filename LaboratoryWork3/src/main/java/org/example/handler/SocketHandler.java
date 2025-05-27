package org.example.handler;

import org.example.client.SocketClient;
import org.example.servers.SocketServer;

import java.io.Closeable;
import java.io.IOException;

public class SocketHandler implements ILogHandler, Closeable {

    private final SocketClient socketClient;
    private final SocketServer socketServer;
    private final Thread serverThread;

    public SocketHandler(){
        socketServer = new SocketServer(4004);
        serverThread = new Thread(socketServer::run);
        serverThread.start();

        socketClient = new SocketClient();
    }

    @Override
    public void handle(String text) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        socketClient.sendMessage(text);
    }

    @Override
    public void close() throws IOException {
        socketClient.close();
        socketServer.close();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
