package co.edu.uptc.presenter;
import co.edu.uptc.model.SystemPrincipal;
import co.edu.uptc.persistence.LoadData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Presenter {

    private final int PUERTO = 1234;
    private ServerSocket serverSocket;
    private SystemPrincipal sPrincipal;
    private LoadData loadData;

    public Presenter() throws IOException {
        this.serverSocket = new ServerSocket(PUERTO);
        this.sPrincipal = new SystemPrincipal();
        this.loadData = new LoadData();
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ClientThread clientThread = new ClientThread(socket, sPrincipal, loadData);
            clientThread.start();
        }
    }

}
