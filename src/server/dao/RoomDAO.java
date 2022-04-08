package server.dao;

import model.Room;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends DAO {
    private static RoomDAO roomDAO;

    public RoomDAO() {
    }

    public static RoomDAO getInstance() {
        if (roomDAO == null) {
            roomDAO = new RoomDAO();
        }
        return roomDAO;
    }

    public List<Room> getRoomList(User user) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.id, r.name, r.type " +
                "FROM dbo.[ROOM] AS r INNER JOIN dbo.[ROOM_USER] AS ru " +
                "ON r.id = ru.room_id " +
                "WHERE r.type <> 0 AND ru.user_id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setType(rs.getInt("type"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {}
        }
        return rooms;
    }

    public Room getRoomById(int roomId) {
        Room room = new Room();
        String sql = "SELECT r.id, r.name " +
                "FROM dbo.[ROOM] AS r " +
                "WHERE r.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            if (rs.next()) {
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {}
        }
        return room;
    }

    public int insert(Room room) {
        int id = -1;
        String sql = "INSERT INTO dbo.[ROOM](name, type) VALUES (?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, room.getName());
            ps.setInt(2, room.getType());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                con.commit();
                id = rs.getInt(1);
            }
        } catch(SQLException e) {
            try {
                con.rollback();
            } catch (SQLException se) { }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException se) { }
            return id;
        }
    }

    public void update(Room room) {
        String sql = "UPDATE dbo.[ROOM] SET name = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, room.getName());
            ps.setInt(2, room.getId());
            ps.executeUpdate();
            con.commit();
        } catch(SQLException e) {
            try {
                con.rollback();
            } catch (SQLException se) { }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException se) { }
        }
    }

    public void insertRoomUser(Room room, User user) {
        String sql = "INSERT INTO dbo.[ROOM_USER](room_id, user_id) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            con.commit();
        } catch(SQLException e) {
            try {
                con.rollback();
            } catch (SQLException se) { }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException se) { }
        }
    }

    public void deleteRoomUser(Room room, User user) {
        String sql = "DELETE FROM dbo.[ROOM_USER] WHERE room_id = ? AND user_id = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            con.commit();
        } catch(SQLException e) {
            try {
                con.rollback();
            } catch (SQLException se) { }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException se) { }
        }
    }
}
