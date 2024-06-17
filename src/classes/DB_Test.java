/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import classes.tests.Test;
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
public class DB_Test {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/DB";
    private static final String DB_USER = "nbuser";
    private static final String DB_PASSWORD = "nbuser";
    private static ArrayList<Test> testList;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }
    
    public static void addTest(String name, int[] questionidlist) {
        getTestList();
        String query = "INSERT INTO APP.TESTS (NAME, QUESTIONIDLIST, ACTIVE)"
                + "VALUES (?, ?, ?)";
        try ( Connection conn = getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            String buf = "";
            for (int i = 0; i < questionidlist.length; i++) {
                buf += questionidlist[i] + " ";
            }
            pstmt.setString(2, buf);
            pstmt.setBoolean(3, false);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        getTestList();
    }
    
    // Testa datu mainīšana (kad skolotājs nospiež pogu "Sākt")
    public static void update(int id, String name, int[] questionIdList, boolean active) {
        try ( Connection conn = getConnection();  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            String buf = "";
            for (int i = 0; i < questionIdList.length; i++) {
                buf += questionIdList[i] + " ";
            }
            stmt.executeUpdate("UPDATE APP.TESTS SET NAME = '" + name + "', QUESTIONIDLIST = '" + buf + "', ACTIVE = '" + active + "' WHERE id = " + id);
        } catch (SQLException e) {
            System.out.println(e);
        }
            getTestList();
    }
    
    // Testa veikšanas atļaujas pārbaude
    public static void activate(int id, boolean active) {
        try ( Connection conn = getConnection();  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.executeUpdate("UPDATE APP.TESTS SET ACTIVE = " + active + " WHERE id = " + id);
            getTestList();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void delete(int id) {
        String query = "DELETE FROM APP.TESTS WHERE id = ?";
        try ( Connection conn = getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        getTestList();
    }
    
    // Testu pārbaude sarakstā
    public static ArrayList<Test> getTestList() {
        testList = new ArrayList<Test>();
        String query = "SELECT * FROM APP.TESTS";
        try ( Connection conn = getConnection();  PreparedStatement pstmt = conn.prepareStatement(query);  ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String[] buf = rs.getString(3).split(" ");
                int[] questionIDList = new int[buf.length];
                for (int i = 0; i < buf.length; i++) {
                    questionIDList[i] = Integer.parseInt(buf[i]);
                }
                boolean active = rs.getBoolean(4);
                testList.add(new Test(id, name, questionIDList, active));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return testList;
    }
    
    public static boolean isTestExists(int id) {
        getTestList();
        for (int i = 0; i < testList.size(); i++) {
            if (testList.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }
}

/*
CREATE TABLE "TESTS"
(
    "ID" INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    "NAME" VARCHAR(50),
    "QUESTIONIDLIST" VARCHAR(150),
    "ACTIVE" BOOLEAN
)
*/