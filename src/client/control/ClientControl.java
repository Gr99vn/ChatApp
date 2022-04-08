package client.control;

import client.view.ChatBoxView;
import model.DataWrapper;
import model.Room;
import model.User;
import utils.Const;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;

public class ClientControl {
    private String hostname;
    private int port;
    private User clientUser;
    private ObjectOutputStream objectOutputStream;

    public ClientControl() {
        this.hostname = Const.HOSTNAME;
        this.port = Const.PORT;
        this.clientUser = new User();
    }

    public ClientControl(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public User getClientUser() {
        return clientUser;
    }

    public void setClientUser(User clientUser) {
        this.clientUser = clientUser;
    }

    public void sendData(DataWrapper dataWrapper) {
        try {
            objectOutputStream.writeObject(dataWrapper);
        } catch (IOException ie) {
            showConsoleMessage("I/O Error, fn: sendData: " + ie.getMessage());
        }
    }

    public boolean openSocketConnection() {
        try {
            Socket clientSocket = new Socket(hostname, port);

            showConsoleMessage("Connected to the chat server");

            new ReadThread(clientSocket, this).start();

            OutputStream outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

            return true;
        } catch (UnknownHostException ex) {
            showConsoleMessage("ServerControl not found: " + ex.getMessage());
            return false;
        } catch (IOException ex) {
            showConsoleMessage("I/O Error, fn: openSocket : " + ex.getMessage());
            return false;
        }
    }

    public void showConsoleMessage(String msg) {
        System.out.println("Message: " + msg);
    }
}
