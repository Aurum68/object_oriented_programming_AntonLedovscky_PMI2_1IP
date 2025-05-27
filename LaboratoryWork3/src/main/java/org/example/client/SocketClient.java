package org.example.client;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient implements Closeable {
    private final Socket clientSocket;
    private final DataOutputStream output;

    public SocketClient() {
        try {
            clientSocket = new Socket("localhost", 4004);
            output = new DataOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        if (message == null) throw new NullPointerException("message is null");

        try {
            output.writeUTF(message);
            output.flush();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
        System.out.println("Client closed");
        output.close();
    }
}
