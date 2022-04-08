package server.control;

import model.DataWrapper;
import model.User;
import utils.Const;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerControl {
    private int port;
    private ServerSocket serverSocket;
    private Set<UserThread> userThreads = new HashSet<>();

    public ServerControl() {
        this.port = Const.PORT;
    }


    public void openServerConnection() {
        try {
            serverSocket = new ServerSocket(port);

            showLog(String.format(
                    "Chat Server on address: %s, port: %d is listening..",
                    serverSocket.getInetAddress().getHostAddress(), port
            ));

            while (true) {
                Socket clientSocket = serverSocket.accept();

                showLog(String.format(
                        "A client connected with address: %s, name: %s, port: %d.",
                        clientSocket.getInetAddress().getHostAddress(),
                        clientSocket.getInetAddress().getHostName(),
                        clientSocket.getPort()
                ));

                UserThread newUser = new UserThread(clientSocket, this);
                userThreads.add(newUser);
                newUser.start();

            }
        } catch (IOException ie) {

        }
    }

    public void removeUser(UserThread userThread) {
        userThreads.remove(userThread);
    }

    public void broadcastData(DataWrapper dataWrapper, UserThread excludeUser) {
        for (UserThread userThread : userThreads) {
            if (!userThread.equals(excludeUser)) {
                userThread.sendDataBack(dataWrapper);
            }
        }
    }

    public UserThread getThread(User user) {
        if (user != null) {
            for (UserThread userThread : userThreads) {
                if (userThread.getUser().getId() == user.getId()) {
                    return userThread;
                }
            }
            return null;
        }
        return null;
    }

    public void showLog(String log) {
        System.out.println(log);
    }
}
