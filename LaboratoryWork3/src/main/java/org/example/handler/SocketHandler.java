package org.example.handler;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHandler implements ILogHandler, Closeable {

    private final Socket clientSocket;
    private final DataOutputStream output;

    public SocketHandler() {
        try {
            clientSocket = new Socket("localhost", 4004);
            output = new DataOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(String text) {
        if (text == null) throw new NullPointerException();

        try {
            output.writeUTF(text);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
        System.out.println("Socket closed");
        output.close();
    }
}
