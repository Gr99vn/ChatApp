package server.dao;

import model.Message;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO extends DAO {
    private static MessageDAO messageDAO;

    public MessageDAO() {
    }

    public static MessageDAO getInstance() {
        if (messageDAO == null) {
            messageDAO = new MessageDAO();
        }
        return messageDAO;
    }

    public List<Message> getRoomMessages(int roomId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.id, m.room_id, m.user_id, m.content, m.created_time " +
                "FROM dbo.[MESSAGE] AS m INNER JOIN dbo.[ROOM] AS r " +
                "ON m.room_id = r.id " +
                "WHERE r.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setRoomId(rs.getInt("room_id"));
                message.setOwnerId(rs.getInt("user_id"));
                message.setContent(rs.getString("content"));
                message.setCreatedDate(rs.getTimestamp("created_time"));
                messages.add(message);
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
        return messages;
    }

    public int insertMessage(Message message) {
        int id = 0;
        String sql = "INSERT INTO dbo.[MESSAGE](room_id, user_id, content) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getRoomId());
            ps.setInt(2, message.getOwnerId());
            ps.setString(3, message.getContent());
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

    public Message getMessage(int messageId) {
        Message message = new Message();
        String sql = "SELECT m.id, m.room_id, m.user_id, m.content, m.created_time " +
                "FROM dbo.[MESSAGE] AS m " +
                "WHERE m.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, messageId);
            rs = ps.executeQuery();
            if (rs.next()) {
                message.setId(rs.getInt("id"));
                message.setRoomId(rs.getInt("room_id"));
                message.setOwnerId(rs.getInt("user_id"));
                message.setContent(rs.getString("content"));
                message.setCreatedDate(rs.getTimestamp("created_time"));
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {}
        }
        return message;
    }
}
