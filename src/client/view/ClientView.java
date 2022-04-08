package client.view;

import client.control.ClientControl;


public class ClientView {
    private static ClientView clientView;

    public static ClientView getInstance() {
        if (clientView == null) {
            clientView = new ClientView();
        }
        return clientView;
    }

    private ClientControl clientControl;

    public ClientView() {
        super();

        clientControl = new ClientControl();

        if (clientControl.openSocketConnection()) {
            LoginView.getInstance(clientControl);
        } else {
            closeClient();
        }
    }

    public void closeClient() {
        if (clientControl != null) {
            clientControl = null;
        }
    }
}