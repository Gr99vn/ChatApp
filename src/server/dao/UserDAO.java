package server.dao;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.User;
import sun.security.krb5.internal.PAForUserEnc;

public class UserDAO extends DAO{
    private static UserDAO userDAO;

    public UserDAO() {
        super();
    }

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }
     
    public User checkLogin(User user) {
        String sql = "SELECT  id, name FROM dbo.[USER] WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
             
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public int insert(User user) {
        int id = -1;
        String sql = "INSERT INTO dbo.[USER](name, username, password) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
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

    public List<User> getFriends(User user) {
        List<User> friendList = new ArrayList<>();
        String sql = "SELECT u.id, u.name FROM dbo.[USER] AS u INNER JOIN dbo.[FRIENDSHIP] AS f " +
                "ON u.id = f.friend_id " +
                "WHERE f.user_id = ? " +
                "UNION " +
                "SELECT u.id, u.name FROM dbo.[USER] AS u INNER JOIN dbo.[FRIENDSHIP] AS f  " +
                "ON u.id = f.user_id " +
                "WHERE f.friend_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User friend = new User();
                friend.setId(rs.getInt("id"));
                friend.setName(rs.getString("name"));
                friendList.add(friend);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return friendList;
    }

    public List<User> getRoomUsers(int roomId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.id, u.name " +
                "FROM ((dbo.[ROOM] AS r INNER JOIN dbo.[ROOM_USER] AS ru ON r.id = ru.room_id) " +
                "INNER JOIN dbo.[USER] AS u ON ru.user_id = u.id) " +
                "WHERE r.id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                users.add(user);
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
        return users;
    }

    public User getById(int id) {
        User user = new User(id);
        String sql = "SELECT id, name FROM dbo.[USER] WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setName(rs.getString("name"));
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
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name FROM dbo.[USER]";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                users.add(user);
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
        return users;
    }

    public List<User> searchByName(String name) {
        String searchExp = "%" + name.toLowerCase() + "%";
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name FROM dbo.[USER] WHERE LOWER(name) LIKE ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, searchExp);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                users.add(user);
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
        return users;
    }
}