package utils;

public class Const {
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "god123";
    public static final String SERVER = "GHOSTMACHINE\\SQLEXPRESS:1433";
    public static final String DBNAME = "CHAT_APP";

    public static final String HOSTNAME = "localhost";
    public static final int PORT = 5000;

    public static final int DOUBLE_ROOM = 0;
    public static final int MULTIPLE_ROOM = 1;

    public static final int LOGIN = 2;
    public static final int RETURN_LOGIN_RESULT = 3;
    public static final int GET_FRIEND_LIST = 4;
    public static final int RETURN_FRIEND_LIST = 5;
    public static final int RETURN_FRIENDSHIP_LIST = 6;
    public static final int GET_ROOM_LIST = 7;
    public static final int RETURN_ROOM_LIST = 8;

    public static final int GET_HOME_VIEW_DATA = 10;
    public static final int RETURN_HOME_VIEW_DATA = 11;
    
    public static final int GET_CHAT_BOX_DATA = 21;
    public static final int RETURN_CHAT_BOX_DATA = 22;

    public static final int SEND_MESSAGE = 23;
    public static final int RETURN_MESSAGE = 24;

    public static final int REGISTER = 30;
    public static final int RETURN_REGISTER_RESULT = 31;

    public static final int GET_ALL_USER = 40;
    public static final int RETURN_RESULT_USER = 41;
    public static final int SEARCH_USER = 42;
    public static final int SEND_FRIEND_REQUEST = 43;
    public static final int RETURN_SEND_FRIEND_REQUEST = 44;
    public static final int NEW_FRIEND_REQUEST = 45;
    public static final int GET_RECEIVED_FRIEND_REQUEST_LIST = 46;
    public static final int RETURN_RECEIVED_FRIEND_REQUEST_LIST = 47;
    public static final int GET_SENT_FRIEND_REQUEST_LIST = 48;
    public static final int RETURN_SENT_FRIEND_REQUEST_LIST = 49;
    public static final int ACCEPT_RECEIVED_FRIEND_REQUEST = 50;
    public static final int REJECT_RECEIVED_FRIEND_REQUEST = 51;
    public static final int RETURN_ACCEPT_RECEIVED_FRIEND_REQUEST = 52;
    public static final int RETURN_REJECT_RECEIVED_FRIEND_REQUEST = 53;
    public static final int CANCEL_SENT_FRIEND_REQUEST = 54;
    public static final int SEND_UNFRIEND_REQUEST = 55;
    public static final int RETURN_UNFRIEND_REQUEST_RESULT = 56;
    public static final int GET_FULLY_ROOM_LIST = 57;
    public static final int RETURN_FULLY_ROOM_LIST_RESULT = 58;
    public static final int CREATE_MULTIPLE_MEMBER_ROOM = 59;
    public static final int RETURN_CREATE_MULTIPLE_MEMBER_ROOM = 60;
    public static final int MODIFY_MULTIPLE_MEMBER_ROOM = 61;
    public static final int RETURN_MODIFY_MULTIPLE_MEMBER_ROOM = 62;
    public static final int SEND_LEAVE_ROOM_REQUEST = 63;
    public static final int RETURN_LEAVE_ROOM_RESULT = 64;
    public static final int CLOSE_ROOM_CHAT = 65;

    public static final int MODE_VIEW_RECEIVED_FRIEND_REQUEST = 200;
    public static final int MODE_VIEW_SENT_FRIEND_REQUEST = 201;
    public static final String FRIEND_REQUEST_SENDER = "sender_id";
    public static final String FRIEND_REQUEST_RECEIVER = "receiver_id";

    public static final int LOGIN_VIEW = 500;
    public static final int HOME_VIEW = 501;
    public static final int CHAT_BOX_VIEW = 502;
    public static final int REGISTER_VIEW = 503;
    public static final int FRIEND_VIEW = 504;
    public static final int FRIEND_REQUEST_VIEW = 505;
    public static final int SENT_FRIEND_REQUEST_VIEW = 505;
    public static final int RECEIVED_FRIEND_REQUEST_VIEW = 506;
    public static final int ROOM_VIEW = 507;
    public static final int ROOM_MANAGEMENT_VIEW = 508;
}
