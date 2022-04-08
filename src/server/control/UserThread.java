package server.control;

import model.*;
import server.service.*;
import utils.Const;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserThread extends Thread {
    private Socket clientSocket;
    private ServerControl serverControl;
    private ObjectOutputStream objectOutputStream;
    private User user;

    public UserThread(Socket clientSocket, ServerControl serverControl) {
        this.clientSocket = clientSocket;
        this.serverControl = serverControl;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            OutputStream outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

            DataWrapper dataWrapper;

            while (true) {
                dataWrapper = (DataWrapper) objectInputStream.readObject();
                switch (dataWrapper.getFlag()) {
                    case Const.LOGIN:
                        processLogin(dataWrapper);
                    case Const.GET_HOME_VIEW_DATA:
                        returnHomeFrmData();
                        break;
                    case Const.GET_CHAT_BOX_DATA:
                        returnChatBoxData(dataWrapper);
                        break;
                    case Const.SEND_MESSAGE:
                        processSendMessage(dataWrapper);
                        break;
                    case Const.REGISTER:
                        processRegister(dataWrapper);
                        break;
                    case Const.GET_ALL_USER:
                        returnAllUsers();
                        break;
                    case Const.SEARCH_USER:
                        processSearchUser(dataWrapper);
                        break;
                    case Const.SEND_FRIEND_REQUEST:
                        processSendFriendRequest(dataWrapper);
                        break;
                    case Const.GET_RECEIVED_FRIEND_REQUEST_LIST:
                        returnReceivedFriendRequestList();
                        break;
                    case Const.GET_SENT_FRIEND_REQUEST_LIST:
                        returnSentFriendRequestList();
                        break;
                    case Const.ACCEPT_RECEIVED_FRIEND_REQUEST:
                        processAcceptReceivedFriendRequest(dataWrapper);
                        break;
                    case Const.REJECT_RECEIVED_FRIEND_REQUEST:
                        processRejectReceivedFriendRequest(dataWrapper);
                        break;
                    case Const.CANCEL_SENT_FRIEND_REQUEST:
                        processCancelSentFriendRequest(dataWrapper);
                        break;
                    case Const.SEND_UNFRIEND_REQUEST:
                        processUnfriendRequest(dataWrapper);
                        break;
                    case Const.GET_FULLY_ROOM_LIST:
                        returnFullyRoomList();
                        break;
                    case Const.CREATE_MULTIPLE_MEMBER_ROOM:
                        processCreateMultipleMemberRoom(dataWrapper);
                        break;
                    case Const.MODIFY_MULTIPLE_MEMBER_ROOM:
                        processModifyMultipleMemberRoom(dataWrapper);
                        break;
                    case Const.SEND_LEAVE_ROOM_REQUEST:
                        processLeaveRoom(dataWrapper);
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            disconnect();
            serverControl.showLog("UserThread disconnected: " + (user == null ? "Anonymous" : user.getName()));
        }
    }

    public void closeRoomChat(Room room) {
        sendDataBack(new DataWrapper(
                Const.CLOSE_ROOM_CHAT,
                room
        ));
    }

    private void processLeaveRoom(DataWrapper dataWrapper) {
        Room leaveRoomInfo = (Room) dataWrapper.getData();
        User leftUser = leaveRoomInfo.getRoomUsers().get(0);
        RoomService.getInstance().deleteRoomUser(leaveRoomInfo, leftUser);
        returnRoomList();
        returnFullyRoomList();
        closeRoomChat(leaveRoomInfo);

        Message message = new Message();
        message.setRoomId(leaveRoomInfo.getId());
        message.setOwnerId(leftUser.getId());
        message.setContent(String.format("%s left the room!", leftUser.getName()));
        message = MessageService.getInstance().addMessage(message);

        for (User member : UserService.getInstance().getRoomUsers(leaveRoomInfo.getId())) {
            UserThread memberThread = serverControl.getThread(member);
            if (memberThread != null) {
                memberThread.returnMessage(message);
                memberThread.returnFullyRoomList();
            }
        }

        sendDataBack(new DataWrapper(
                Const.RETURN_LEAVE_ROOM_RESULT,
                String.format("Leave %s success!", leaveRoomInfo.getName())
        ));
    }

    private void processModifyMultipleMemberRoom(DataWrapper dataWrapper) {
        List<Object> objects = (ArrayList<Object>) dataWrapper.getData();
        User action = (User) objects.get(0);
        Room room = (Room) objects.get(1);
        List<User> removedUsers = (ArrayList<User>) objects.get(2);
        List<User> invitedUsers = (ArrayList<User>) objects.get(3);


        String messageContent = "Room updated: ";
        String currentName = RoomService.getInstance().getRoomById(room.getId()).getName();
        String newName = room.getName();

        boolean hasChangeName = !newName.equals(currentName);

        if (hasChangeName) {
            RoomService.getInstance().updateRoom(room);
            messageContent += String.format("Room name changed from '%s' to %s.", currentName, newName);
        }

        if (!removedUsers.isEmpty()) {
            messageContent += String.format("Member removed (%d): ", removedUsers.size());
            for (User user : removedUsers) {
                int index = removedUsers.indexOf(user);
                if (index >= 1) {
                    messageContent += ",";
                }
                messageContent += (" " + user.getName());
                RoomService.getInstance().deleteRoomUser(room, user);
                if (serverControl.getThread(user) != null) {
                    serverControl.getThread(user).returnRoomList();
                }
            }
        }

        if (!invitedUsers.isEmpty()) {
            messageContent += String.format(". Member added (%d): ", invitedUsers.size());
            for (User user : invitedUsers) {
                int index = invitedUsers.indexOf(user);
                if (index >= 1) {
                    messageContent += ",";
                }
                messageContent += (" " + user.getName());
                RoomService.getInstance().insertRoomUser(room, user);
                if (serverControl.getThread(user) != null) {
                    serverControl.getThread(user).returnRoomList();
                }
            }
        }

        Message message = new Message();
        message.setRoomId(room.getId());
        message.setOwnerId(action.getId());
        message.setContent(messageContent);
        message = MessageService.getInstance().addMessage(message);

        for (User member : UserService.getInstance().getRoomUsers(room.getId())) {
            UserThread memberThread = serverControl.getThread(member);
            if (memberThread != null) {
                if (hasChangeName) {
                    memberThread.returnRoomList();
                }
                memberThread.returnMessage(message);
            }
        }

        sendDataBack(new DataWrapper(
                Const.RETURN_MODIFY_MULTIPLE_MEMBER_ROOM,
                "Modify room success"
        ));

    }

    private void processCreateMultipleMemberRoom(DataWrapper dataWrapper) {
        Room room = (Room) dataWrapper.getData();
        RoomService.getInstance().addRoom(room);
        sendDataBack(new DataWrapper(
                Const.RETURN_CREATE_MULTIPLE_MEMBER_ROOM,
                String.format("Create room %s success", room.getName())
        ));
        returnRoomList();
        for (User member : room.getRoomUsers()) {
            if (member.getId() != this.user.getId()) {
                UserThread memberThread = serverControl.getThread(member);
                if (memberThread != null) {
                    memberThread.sendDataBack(new DataWrapper(
                            Const.RETURN_CREATE_MULTIPLE_MEMBER_ROOM,
                            String.format("You have been invited to room chat named %s!", room.getName())
                    ));
                    memberThread.returnRoomList();
                }
            }
        }
    }

    private void returnFullyRoomList() {
        List<Room> rooms = RoomService.getInstance().getFullyRooms(this.user);
        sendDataBack(new DataWrapper(
                Const.RETURN_FULLY_ROOM_LIST_RESULT,
                rooms
        ));
    }

    private void processUnfriendRequest(DataWrapper dataWrapper) {
        Friendship friendship = (Friendship) dataWrapper.getData();
        if (getUser().getId() == friendship.getUser().getId()) {
            // check has friendship before
            friendship = FriendshipService.getInstance().getFriendship(friendship);
            if (friendship.getId() != 0 ) {
                friendship.setStatus(Friendship.INACTIVE);
                FriendshipService.getInstance().updateFriendship(friendship);

                // return for self client - receiver
                if (this != null) {
                    returnFriendshipList();
                    sendDataBack(new DataWrapper(
                            Const.RETURN_UNFRIEND_REQUEST_RESULT,
                            "Success"
                    ));
                }

                // return for sender client
                UserThread receiverThread = serverControl.getThread(friendship.getFriend());
                if (receiverThread != null) {
                    receiverThread.returnFriendshipList();
                }
            }
        }
    }

    private void processCancelSentFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        if (getUser().getId() == friendRequest.getSender().getId()) {
            FriendRequestService.getInstance().removeFriendRequest(friendRequest);

            returnSentFriendRequestList();

            // return for receiver
            UserThread receiverThread = serverControl.getThread(friendRequest.getReceiver());
            if (receiverThread != null) {
                receiverThread.returnReceivedFriendRequestList();
            }
        }
    }

    private void processRejectReceivedFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        if (getUser().getId() == friendRequest.getReceiver().getId()) {
            friendRequest.setStatus(FriendRequest.REJECT);
            FriendRequestService.getInstance().updateFriendRequest(friendRequest);

            // return for self
            if (this != null) {
                returnReceivedFriendRequestList();
                notifyRejectMessage(friendRequest);
            }

            // return for sender
            UserThread senderThread = serverControl.getThread(friendRequest.getSender());
            if (senderThread != null) {
                senderThread.returnSentFriendRequestList();
                senderThread.notifyRejectMessage(friendRequest);
            }

        }
    }

    public void notifyRejectMessage(FriendRequest friendRequest) {
        sendDataBack(new DataWrapper(
                Const.RETURN_REJECT_RECEIVED_FRIEND_REQUEST,
                friendRequest
        ));
    }

    public void notifyAcceptMessage(FriendRequest friendRequest) {
        sendDataBack(new DataWrapper(
                Const.RETURN_ACCEPT_RECEIVED_FRIEND_REQUEST,
                friendRequest
        ));
    }

    private void processAcceptReceivedFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        if (getUser().getId() == friendRequest.getReceiver().getId()) {
            friendRequest.setStatus(FriendRequest.ACCEPT);
            FriendRequestService.getInstance().updateFriendRequest(friendRequest);

            Friendship friendship = new Friendship();
            friendship.setUser(friendRequest.getSender());
            friendship.setFriend(friendRequest.getReceiver());

            // check has friendship before
            friendship = FriendshipService.getInstance().getFriendship(friendship);
            if (friendship.getId() != 0 && friendship.getRoom().getId() != 0 ) {
                friendship.setStatus(Friendship.ACTIVE);
                FriendshipService.getInstance().updateFriendship(friendship);
            } else {
                Room room = new Room();
                List<User> roomUsers = new ArrayList<>();
                room.setRoomUsers(roomUsers);
                roomUsers.add(friendRequest.getSender());
                roomUsers.add(friendRequest.getReceiver());
                room.setName(friendRequest.getSender().getName() + "_" + friendRequest.getReceiver().getName());
                room.setType(Const.DOUBLE_ROOM);
                room = RoomService.getInstance().addRoom(room);

                friendship.setRoom(room);

                int newFriendShipId = FriendshipService.getInstance().addFriendShip(friendship);
                friendship.setId(newFriendShipId);
            }

            // return for self client - receiver
            if (this != null) {
                returnReceivedFriendRequestList();
                returnFriendshipList();
                notifyAcceptMessage(friendRequest);
            }

            // return for sender client
            UserThread senderThread = serverControl.getThread(friendship.getUser());
            if (senderThread != null) {
                senderThread.returnSentFriendRequestList();
                senderThread.returnFriendshipList();
                senderThread.notifyAcceptMessage(friendRequest);
            }
        }
    }

    public void returnSentFriendRequestList() {
        List<FriendRequest> friendRequests = FriendRequestService.getInstance().getFriendRequests(getUser(), Const.FRIEND_REQUEST_SENDER);
        sendDataBack(new DataWrapper(Const.RETURN_SENT_FRIEND_REQUEST_LIST, friendRequests));
    }

    public void returnReceivedFriendRequestList() {
        List<FriendRequest> friendRequests = FriendRequestService.getInstance().getFriendRequests(getUser(), Const.FRIEND_REQUEST_RECEIVER);
        sendDataBack(new DataWrapper(Const.RETURN_RECEIVED_FRIEND_REQUEST_LIST, friendRequests));
    }

    public void processSendFriendRequest(DataWrapper dataWrapper) {
        FriendRequest friendRequest = (FriendRequest) dataWrapper.getData();
        friendRequest.setStatus(FriendRequest.WAIT);
        friendRequest = FriendRequestService.getInstance().addFriendRequest(friendRequest);
        UserThread senderThread = serverControl.getThread(friendRequest.getSender());
        if (senderThread != null) {
            senderThread.sendDataBack(new DataWrapper(Const.RETURN_SEND_FRIEND_REQUEST, friendRequest));
        }
        UserThread receiverThread = serverControl.getThread(friendRequest.getReceiver());
        if (receiverThread != null) {
            receiverThread.sendDataBack(new DataWrapper(Const.NEW_FRIEND_REQUEST, friendRequest));
        }

    }

    public void processSearchUser(DataWrapper dataWrapper) {
        String searchData = (String) dataWrapper.getData();
        List<User> userSearchResults = UserService.getInstance().searchByName(searchData);
        sendDataBack(new DataWrapper(Const.RETURN_RESULT_USER, userSearchResults));
    }

    public void returnAllUsers() {
        List<User> users = UserService.getInstance().getAllUsers();
        sendDataBack(new DataWrapper(Const.RETURN_RESULT_USER, users));
    }

    public void processRegister(DataWrapper dataWrapper) {
        User newUser = (User) dataWrapper.getData();
        int userId = UserService.getInstance().register(newUser);
        newUser.setId(userId);

        sendDataBack(new DataWrapper(Const.RETURN_REGISTER_RESULT, newUser));
    }

    public void processLogin (DataWrapper dataWrapper) {
        User user = (User) dataWrapper.getData();
        user = UserService.getInstance().checkLogin(user);
        this.user = user;
        this.user.setStatus("on");

        //return check login result
        sendDataBack(new DataWrapper(Const.RETURN_LOGIN_RESULT, this.user));

        //notify online status for friends
        notifyStatusChange(this.user);
    }

    public void notifyStatusChange(User user) {
        List<User> friends = getFriends(user);
        for (User friend : friends) {
            UserThread friendThread = serverControl.getThread(friend);
            if (friendThread != null) {
                friendThread.returnFriendshipList();
            }
        }
    }

    public void returnFriendshipList() {
        List<Friendship> friendships = getFriendships(this.user);
        DataWrapper dataWrapper = new DataWrapper(Const.RETURN_FRIENDSHIP_LIST, friendships);
        sendDataBack(dataWrapper);
    }

    public List<User> getFriends(User user) {
        List<User> friends = UserService.getInstance().getFriends(user);
        for (User friend : friends) {
            setActiveStatus(friend);
        }
        return friends;
    }

    public User setActiveStatus(User user) {
        if (serverControl.getThread(user) != null) {
            user.setStatus("on");
        } else {
            user.setStatus("off");
        }
        return user;
    }

    public List<Friendship> getFriendships(User user) {
        List<Friendship> friendships = FriendshipService.getInstance().getFriendShips(user);
        for (Friendship friendship : friendships) {
            setActiveStatus(friendship.getUser());
            setActiveStatus(friendship.getFriend());
        }
        return friendships;
    }

    public List<Room> processRoomList(User user) {
        return RoomService.getInstance().getRooms(user);
    }

    public void returnRoomList() {
        List<Room> rooms = processRoomList(this.user);
        sendDataBack(new DataWrapper(Const.RETURN_ROOM_LIST, rooms));
    }

    public void returnHomeFrmData() {
        List<Object> returnList = new ArrayList<>();
        List<Friendship> friendships = getFriendships(this.user);
        List<Room> rooms = processRoomList(this.user);
        returnList.add(friendships);
        returnList.add(rooms);
        sendDataBack(new DataWrapper(Const.RETURN_HOME_VIEW_DATA, returnList));
    }

    public void returnChatBoxData(DataWrapper dataWrapper) throws IOException {
        Room room = (Room) dataWrapper.getData();
        List<Object> returnItems = new ArrayList<>();
        List<User> users = UserService.getInstance().getRoomUsers(room.getId());
        List<Message> messages = MessageService.getInstance().getRoomMessages(room.getId());
        returnItems.add(room);
        returnItems.add(users);
        returnItems.add(messages);
        sendDataBack(new DataWrapper(Const.RETURN_CHAT_BOX_DATA, returnItems));
    }

    public void processSendMessage(DataWrapper dataWrapper) throws IOException {
        Message message = (Message) dataWrapper.getData();
        message = MessageService.getInstance().addMessage(message);
        List<User> roomUsers = UserService.getInstance().getRoomUsers(message.getRoomId());
        for (User user : roomUsers) {
            UserThread userThread = serverControl.getThread(user);
            if (userThread != null) {
                userThread.returnMessage(message);
            }
        }
    }

    public void returnMessage(Message message) {
        sendDataBack(new DataWrapper(Const.RETURN_MESSAGE, message));
    }

    public void disconnect() {
        try {
            User disconnectedUser = new User(this.user.getId());
            serverControl.removeUser(this);
            notifyStatusChange(disconnectedUser);
            clientSocket.close();
        } catch (IOException ie) {}
    }

    public void sendDataBack(DataWrapper dataWrapper) {
        try {
            objectOutputStream.writeObject(dataWrapper);
        } catch (IOException e) {
            serverControl.showLog("I/O Error in UserThread: sendDataBack: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
