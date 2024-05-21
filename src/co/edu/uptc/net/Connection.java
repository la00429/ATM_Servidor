package co.edu.uptc.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void connect() throws IOException {
        try {
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new IOException("Connection not established", e);
        }
    }

    public void send(String message) throws IOException {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            output.writeUTF(message);
        } else {
            throw new IOException("Socket is not connected or is closed");
        }
    }

    public String receive() throws IOException {
        String message = new String();
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            message = input.readUTF();
        } else {
            throw new IOException("Socket is not connected or is closed");
        }
        return message;
    }

    public void disconnect() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        } else {
            throw new IOException("Socket is not connected or is closed");
        }
    }
}
