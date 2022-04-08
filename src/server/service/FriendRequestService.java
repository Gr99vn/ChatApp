package server.service;

import model.FriendRequest;
import model.User;
import server.dao.FriendRequestDAO;

import java.util.List;

public class FriendRequestService {
    private static FriendRequestService friendRequestService;

    public static FriendRequestService getInstance() {
        if (friendRequestService == null) {
            friendRequestService = new FriendRequestService();
        }
        return friendRequestService;
    }

    public FriendRequest getById(int id) {
        FriendRequest friendRequest = FriendRequestDAO.getInstance().getById(id);
        return setFullObjectAttribute(friendRequest);
    }

    private FriendRequest setFullObjectAttribute(FriendRequest friendRequest) {
        User sender = UserService.getInstance().getById(friendRequest.getSender().getId());
        User receiver = UserService.getInstance().getById(friendRequest.getReceiver().getId());
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        return friendRequest;
    }

    public FriendRequest addFriendRequest(FriendRequest friendRequest) {
        int id = FriendRequestDAO.getInstance().insertFriendRequest(friendRequest);
        return getById(id);
    }

    public List<FriendRequest> getFriendRequests(User user, String target) {
        List<FriendRequest> friendRequests = FriendRequestDAO.getInstance().getFriendRequests(user, target);
        for (FriendRequest friendRequest : friendRequests) {
            setFullObjectAttribute(friendRequest);
        }
        return friendRequests;
    }

    public void removeFriendRequest(FriendRequest friendRequest) {
        FriendRequestDAO.getInstance().deleteFriendRequest(friendRequest);
    }

    public void updateFriendRequest(FriendRequest friendRequest) {
        FriendRequestDAO.getInstance().updateFriendRequest(friendRequest);
    }
}
