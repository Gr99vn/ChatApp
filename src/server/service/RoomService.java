package server.service;

import model.Room;
import model.User;
import server.dao.RoomDAO;

import java.util.List;

public class RoomService {
    private static RoomService roomService;

    public static RoomService getInstance() {
        if (roomService == null) {
            roomService = new RoomService();
        }
        return roomService;
    }

    public List<Room> getRooms(User user) {
        return RoomDAO.getInstance().getRoomList(user);
    }

    public List<Room> getFullyRooms(User user) {
        List<Room> rooms = getRooms(user);
        for (Room room : rooms) {
            room.setRoomUsers(UserService.getInstance().getRoomUsers(room.getId()));
        }
        return rooms;
    }

    public Room getRoomById(int roomId) {
        return RoomDAO.getInstance().getRoomById(roomId);
    }

    public Room addRoom(Room room) {
        int id = RoomDAO.getInstance().insert(room);
        room.setId(id);
        for (User user : room.getRoomUsers()) {
            RoomDAO.getInstance().insertRoomUser(room, user);
        }
        return room;
    }

    public void updateRoom(Room room) {
        RoomDAO.getInstance().update(room);
    }

    public void insertRoomUser(Room room, User user) {
        RoomDAO.getInstance().insertRoomUser(room, user);
    }

    public void deleteRoomUser(Room room, User user) {
        RoomDAO.getInstance().deleteRoomUser(room, user);
    }
}
