package server.dao;

import model.FriendRequest;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO extends DAO {
    private static FriendRequestDAO friendRequestDAO;

    public static FriendRequestDAO getInstance() {
        if (friendRequestDAO == null) {
            friendRequestDAO = new FriendRequestDAO();
        }
        return friendRequestDAO;
    }

    public FriendRequest getById(int id) {
        FriendRequest friendRequest = new FriendRequest();
        String sql = "SELECT id, sender_id, receiver_id, sent_time, status " +
                "FROM dbo.[FRIEND_REQUEST] " +
                "WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                friendRequest.setId(rs.getInt("id"));
                friendRequest.setSender(
                        new User(rs.getInt("sender_id"))
                );
                friendRequest.setReceiver(
                        new User(rs.getInt("receiver_id"))
                );
                friendRequest.setSentTime(rs.getTimestamp("sent_time"));
                friendRequest.setStatus(rs.getInt("status"));
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
        return friendRequest;
    }

    public List<FriendRequest> getFriendRequests(User user, String target) {
        List<FriendRequest> friendRequests = new ArrayList<>();
        String sql = String.format("SELECT id, sender_id, receiver_id, sent_time, status " +
                "FROM dbo.[FRIEND_REQUEST] " +
                "WHERE %s = ? AND status = 0"
                , target);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                FriendRequest friendRequest = new FriendRequest();
                friendRequest.setId(rs.getInt("id"));
                friendRequest.setSender(
                        new User(rs.getInt("sender_id"))
                );
                friendRequest.setReceiver(
                        new User(rs.getInt("receiver_id"))
                );
                friendRequest.setSentTime(rs.getTimestamp("sent_time"));
                friendRequest.setStatus(rs.getInt("status"));
                friendRequests.add(friendRequest);
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
        return friendRequests;
    }

    public int insertFriendRequest(FriendRequest friendRequest) {
        int id = 0;
        String sql = "INSERT INTO dbo.[FRIEND_REQUEST] (sender_id, receiver_id, status) " +
                "VALUES (?, ?, ?);";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, friendRequest.getSender().getId());
            ps.setInt(2, friendRequest.getReceiver().getId());
            ps.setInt(3, friendRequest.getStatus());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                con.commit();
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {}
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {}
        }
        return id;
    }

    public void deleteFriendRequest(FriendRequest friendRequest) {
        String sql = "DELETE FROM dbo.[FRIEND_REQUEST] WHERE id = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, friendRequest.getId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {}
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }

    public void updateFriendRequest(FriendRequest friendRequest) {
        String sql = "UPDATE dbo.[FRIEND_REQUEST] SET status = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setInt(1, friendRequest.getStatus());
            ps.setInt(2, friendRequest.getId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {}
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }
}
