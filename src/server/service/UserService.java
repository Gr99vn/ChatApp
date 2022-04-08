package server.service;

import model.User;
import server.dao.UserDAO;

import java.util.List;

public class UserService {
    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public User getById(int id) {
        return UserDAO.getInstance().getById(id);
    }

    public User checkLogin(User user) {
        return UserDAO.getInstance().checkLogin(user);
    }

    public List<User> getFriends(User user) {
        return UserDAO.getInstance().getFriends(user);
    }

    public List<User> getRoomUsers(int roomId) {
        return UserDAO.getInstance().getRoomUsers(roomId);
    }

    public int register(User user) {
        return UserDAO.getInstance().insert(user);
    }

    public List<User> getAllUsers() {
        return UserDAO.getInstance().getAllUsers();
    }

    public List<User> searchByName(String name) {
        return UserDAO.getInstance().searchByName(name);
    }
}
