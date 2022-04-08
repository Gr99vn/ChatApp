package server.service;

import model.Friendship;
import model.User;
import server.dao.FriendshipDAO;

import java.util.List;

public class FriendshipService {
    private static FriendshipService friendshipService;

    public static FriendshipService getInstance() {
        if (friendshipService == null) {
            friendshipService = new FriendshipService();
        }
        return friendshipService;
    }

    public int addFriendShip(Friendship friendship) {
        return FriendshipDAO.getInstance().insertFriendShip(friendship);
    }

    public List<Friendship> getFriendShips(User user) {
        return FriendshipDAO.getInstance().getFriendShips(user);
    }

    public Friendship getFriendship(Friendship friendship) {
        return FriendshipDAO.getInstance().getFriendship(friendship);
    }

    public void updateFriendship(Friendship friendship) {
        FriendshipDAO.getInstance().updateFriendShip(friendship);
    }
}
