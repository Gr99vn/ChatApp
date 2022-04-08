package server.view;

import server.control.ServerControl;

public class ServerView {
    public ServerView() {
        ServerControl serverControl = new ServerControl();
        serverControl.openServerConnection();
    }
}
