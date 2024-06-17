/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import classes.users.Student;
import classes.users.Teacher;
import classes.users.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author anna.rinkevica.PR-21
 * @since 17.06.2024
 *
 */
public class DB_User {

    private static final String DB_URL = "jdbc:derby://localhost:1527/DB";
    private static final String DB_USER = "nbuser";
    private static final String DB_PASSWORD = "nbuser";
    private static ArrayList<User> userList;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }

    public static User addUser(String login, String password, boolean rights) {
        String query = "INSERT INTO APP.USERS (LOGIN, PASSWORD, RIGHTS) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, rights);
            pstmt.executeUpdate();
            getUserList();
            System.out.println(rights);
            return userList.get(userList.size() - 1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void updateUser(int id, String firstName, String lastName, String eMail) {
        String query = "UPDATE APP.USERS SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ? WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, eMail);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            getUserList();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void delete(int id) {
        String query = "DELETE FROM APP.USERS WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            userList.removeIf(user -> user.getId() == id);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static User getUser(String login, String password) {
        getUserList();
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User getUser(int id) {
        getUserList();
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void getUserList() {
        userList = new ArrayList<User>();
        String query = "SELECT * FROM APP.USERS";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                String email = rs.getString(6);
                boolean rights = rs.getBoolean(7);
                int[] pointlist = null;
                if (rs.getString(8) != null) {
                    String[] buf = rs.getString(8).split(" ");

                    pointlist = new int[buf.length];
                    for (int i = 0; i < buf.length; i++) {
                        pointlist[i] = Integer.parseInt(buf[i]);
                    }
                }
                if (rights) {
                    userList.add(new Teacher(id, login, password, firstName, lastName, email, rights, pointlist));
                } else {
                    userList.add(new Student(id, login, password, firstName, lastName, email, rights, pointlist));
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean isLoginExists(String login) {
        getUserList();
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    // Punktu pievienošana par jautājumu aizpildīšanu
    public static void addPointList(int id) {
        String query = "UPDATE APP.USERS SET POINTLIST = ? WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            String buf = "";
            for (int i : getUser(id).getPointlist()) {
                buf += i + " ";
            }
            buf += 0 + " ";
            pstmt.setString(1, buf);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            getUserList();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Punktu skaits par jautājumu aizpildīšanu var atšķirties
    public static void updatePointList(int id, int testid, int points) {
        String query = "UPDATE APP.USERS SET POINTLIST = ? WHERE ID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            getUserList();
            int[] pointlist = getUser(id).getPointlist();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getId() == id) {
                    pointlist[i] = points;

                }
            }
            String buf = "";
            for (int i = 0; i < pointlist.length; i++) {
                buf += pointlist[i] + " ";
            }
            pstmt.setString(1, buf);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            getUserList();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean isPasswordCorrect(String login, String password) {
        getUserList();
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}

/*
CREATE TABLE "USERS"
(
    "ID" INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    "LOGIN" VARCHAR(50) UNIQUE,
    "PASSWORD" VARCHAR(50),
    "FIRSTNAME" VARCHAR(50),
    "LASTNAME" VARCHAR(50),
    "EMAIL" VARCHAR(100) UNIQUE,
    "RIGHTS" BOOLEAN
)
 */
