package org.example.servers;

import org.example.handler.FileHandler;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Closeable {


    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream dataInputStream;

    public SocketServer() {
        try {
            this.serverSocket = new ServerSocket(4004);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() {
        String text = null;
        try {
            this.clientSocket = serverSocket.accept();
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            text = dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileHandler fileHandler = new FileHandler("socketLogs.txt");
        fileHandler.handle(text);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
        clientSocket.close();
        dataInputStream.close();
    }
}
