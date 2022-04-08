package server.dao;

import model.Friendship;
import model.Room;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDAO extends DAO {
    private static FriendshipDAO friendshipDAO;

    public static FriendshipDAO getInstance() {
        if (friendshipDAO == null) {
            friendshipDAO = new FriendshipDAO();
        }
        return friendshipDAO;
    }

    public List<Friendship> getFriendShips(User user) {
        List<Friendship> friendshipList = new ArrayList<>();
        String sql =
                "SELECT u.id AS uid, u.name AS uname, us.id AS usid, us.name AS usname, r.id AS rid, r.name AS rname " +
                "FROM ((dbo.[USER] AS u INNER JOIN dbo.[FRIENDSHIP] AS f ON u.id = f.user_id) " +
                "INNER JOIN dbo.[USER] AS us ON f.friend_id = us.id) INNER JOIN dbo.[ROOM] AS r ON f.room_id = r.id " +
                "WHERE f.user_id = ? AND f.status = 1" +
                "UNION " +
                "SELECT u.id AS uid, u.name AS uname, us.id AS usid, us.name AS usname, r.id AS rid, r.name AS rname " +
                "FROM ((dbo.[USER] AS u INNER JOIN dbo.[FRIENDSHIP] AS f ON u.id = f.user_id)  " +
                "INNER JOIN dbo.[USER] AS us ON f.friend_id = us.id) INNER JOIN dbo.[ROOM] AS r ON f.room_id = r.id " +
                "WHERE f.friend_id = ? AND f.status = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Friendship friendship = new Friendship();
                friendship.setUser(new User(rs.getInt("uid"), rs.getString("uname")));
                friendship.setFriend(new User(rs.getInt("usid"), rs.getString("usname")));
                friendship.setRoom(new Room(rs.getInt("rid"), rs.getString("rname")));
                friendshipList.add(friendship);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return friendshipList;
    }

    public Friendship getFriendship(Friendship friendship) {
        String sql = "SELECT id, room_id, add_friend_time, status FROM dbo.[FRIENDSHIP] " +
                "WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friendship.getUser().getId());
            ps.setInt(2, friendship.getFriend().getId());
            ps.setInt(3, friendship.getFriend().getId());
            ps.setInt(4, friendship.getUser().getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                friendship.setId(rs.getInt("id"));
                friendship.setRoom(new Room(rs.getInt("room_id")));
                friendship.setAddFriendTime(rs.getTimestamp("add_friend_time"));
                friendship.setStatus(rs.getInt("status"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return friendship;
    }

    public int insertFriendShip(Friendship friendship) {
        int id = -1;
        String sql = "INSERT INTO dbo.[FRIENDSHIP](user_id, friend_id, room_id) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, friendship.getUser().getId());
            ps.setInt(2, friendship.getFriend().getId());
            ps.setInt(3, friendship.getRoom().getId());
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

    public void updateFriendShip(Friendship friendship) {
        String sql = "UPDATE dbo.[FRIENDSHIP] SET status = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, friendship.getStatus());
            ps.setInt(2, friendship.getId());
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
