package client.control;

import client.view.*;
import model.DataWrapper;
import model.Message;
import model.Room;
import utils.Const;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReadThread extends Thread {
    private ClientControl clientControl;
    private Socket clientSocket;
    private ObjectInputStream objectInputStream;

    public ReadThread(Socket clientSocket, ClientControl clientControl) {
        this.clientSocket = clientSocket;
        this.clientControl = clientControl;
    }

    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException ie) {
            System.out.println("Error getting input stream: " + ie.getMessage());
            ie.printStackTrace();
        }
        while (true) {
            try {
                DataWrapper dataWrapper = (DataWrapper) objectInputStream.readObject();
                switch (dataWrapper.getFlag()) {
                    case Const.RETURN_LOGIN_RESULT:
                        if (LoginView.isAvailable()) {
                            LoginView.getInstance(clientControl).processLoginResult(dataWrapper);
                        }
                        break;
                    case Const.RETURN_FRIEND_LIST:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processFriendList(dataWrapper);
                        }
                        break;
                    case Const.RETURN_FRIENDSHIP_LIST:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processFriendshipList(dataWrapper);
                        }
                        break;
                    case Const.RETURN_ROOM_LIST:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processRoomList(dataWrapper);
                        }
                        break;
                    case Const.RETURN_HOME_VIEW_DATA:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processHomeViewData(dataWrapper);
                        }
                        break;
                    case Const.RETURN_CHAT_BOX_DATA:
                        List<Object> returnItems = (ArrayList<Object>) dataWrapper.getData();
                        Room room = (Room) returnItems.get(0);
                        if (ChatBoxView.getInstance(clientControl, room) != null) {
                            ChatBoxView.getInstance(clientControl, room).processChatBoxData(returnItems);
                        }
                        break;
                    case Const.RETURN_MESSAGE:
                        Message message = (Message) dataWrapper.getData();
                        if (ChatBoxView.getInstance(clientControl, new Room(message.getRoomId())) != null) {
                            ChatBoxView.getInstance(clientControl, new Room(message.getRoomId()))
                                    .processMessageData(message);
                        }
                        break;
                    case Const.CLOSE_ROOM_CHAT:
                        Room closedRoom = (Room) dataWrapper.getData();
                        if (ChatBoxView.getInstance(clientControl, closedRoom) != null) {
                            ChatBoxView.getInstance(clientControl, closedRoom)
                                    .processCloseRoomChat();
                        }
                        break;
                    case Const.RETURN_REGISTER_RESULT:
                        if (RegisterView.isAvailable()) {
                            RegisterView.getInstance(clientControl).processRegisterResult(dataWrapper);
                        }
                        break;
                    case Const.RETURN_RESULT_USER:
                        if (FriendView.isAvailable()) {
                            FriendView.getInstance(clientControl).processReceivedUsersData(dataWrapper);
                        }
                        break;
                    case Const.RETURN_SEND_FRIEND_REQUEST:
                        if (FriendView.isAvailable()) {
                            FriendView.getInstance(clientControl).processSendFriendRequestResult(dataWrapper);
                        }
                        break;
                    case Const.NEW_FRIEND_REQUEST:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processNewFriendRequest(dataWrapper);
                        }
                        break;
                    case Const.RETURN_SENT_FRIEND_REQUEST_LIST:
                        if (SentFriendRequestView.isAvailable()) {
                            SentFriendRequestView.getInstance(clientControl).processSentFriendRequestList(dataWrapper);
                        }
                        break;
                    case Const.RETURN_RECEIVED_FRIEND_REQUEST_LIST:
                        if (ReceivedFriendRequestView.isAvailable()) {
                            ReceivedFriendRequestView.getInstance(clientControl)
                                    .processReceivedFriendRequestList(dataWrapper);
                        }
                        break;
                    case Const.RETURN_ACCEPT_RECEIVED_FRIEND_REQUEST:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processAcceptReceivedFriendRequest(dataWrapper);
                        }
                        break;
                    case Const.RETURN_REJECT_RECEIVED_FRIEND_REQUEST:
                        if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).processRejectReceivedFriendRequest(dataWrapper);
                        }
                        break;
                    case Const.RETURN_UNFRIEND_REQUEST_RESULT:
                        if (FriendView.isAvailable()) {
                            FriendView.getInstance(clientControl).processUnfriendRequestResult(dataWrapper);
                        }
                        break;
                    case Const.RETURN_FULLY_ROOM_LIST_RESULT:
                        if (RoomView.isAvailable()) {
                            RoomView.getInstance(clientControl).processFullyRoomListResult(dataWrapper);
                        }
                        break;
                    case Const.RETURN_CREATE_MULTIPLE_MEMBER_ROOM:
                    case Const.RETURN_MODIFY_MULTIPLE_MEMBER_ROOM:
                    case Const.RETURN_LEAVE_ROOM_RESULT:
                        if (RoomView.isAvailable()) {
                            RoomView.getInstance(clientControl).announceResult(dataWrapper);
                        }
                        else if (HomeView.isAvailable()) {
                            HomeView.getInstance(clientControl).announceResult(dataWrapper);
                        }
                        break;
                }
            } catch (IOException ie) {
                System.out.println("I/O Error: can not connect to server");
                System.out.println("Exit program..");
                System.exit(0);
            } catch (ClassNotFoundException ce) {
                System.out.println("CNF error, fn: run, file: ReadThread");
            }
        }
    }
}
