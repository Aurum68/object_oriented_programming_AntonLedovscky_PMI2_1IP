package org.example.servers;

import org.example.handler.FileHandler;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Closeable {
    private final ServerSocket serverSocket;
    private volatile boolean running = true;

    public SocketServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (running) {
            try {
                System.out.println("Ожидание клиента...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен: " + clientSocket.getRemoteSocketAddress());

                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                String message = input.readUTF();
                System.out.println("Получено сообщение: " + message);

                // Можно добавить обработку клиента или закрыть его
                clientSocket.close();

            } catch (IOException e) {
                if (running) {
                    System.err.println(e.getMessage());
                } else {
                    System.out.println("Сервер остановлен");
                }
            }
        }
    }

    public void stop() {
        running = false;
        try {
            // закрываем серверный сокет, чтобы прервать accept()
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        stop();
    }
}