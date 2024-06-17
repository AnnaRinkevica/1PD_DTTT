/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import classes.tests.Question;
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
public class DB_Question {

    private static final String DB_URL = "jdbc:derby://localhost:1527/DB";
    private static final String DB_USER = "nbuser";
    private static final String DB_PASSWORD = "nbuser";
    private static ArrayList<Question> questionList;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }

    // Jautājuma datu mainīšana
    public static void update(int id, String text, String[] answers, int rightanswer) {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            String buf = "";
            for (int i = 0; i < answers.length; i++) {
                buf += answers[i] + ";";
            }
            stmt.executeUpdate("UPDATE APP.QUESTIONS SET text = '" + text + "', answers = '" + buf + "', rightanswer = " + rightanswer + " WHERE id = " + id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        getQuestionList();
    }

    public static void delete(int id) {
        String query = "DELETE FROM APP.QUESTIONS WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        getQuestionList();
    }

    public static Question addQuestion(String text, String[] answers, int rightAnswers) {
        String query = "INSERT INTO APP.QUESTIONS (TEXT, ANSWERS, RIGHTANSWER)"
                + "VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, text);
            String buf = "";
            for (int i = 0; i < answers.length; i++) {
                buf += answers[i] + ";";
            }
            pstmt.setString(2, buf);
            pstmt.setInt(3, rightAnswers);
            pstmt.executeUpdate();
            getQuestionList();
            return questionList.get(questionList.size() - 1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // Noskaidrosim, kādi jautājumi ir iekļauti sarakstā
    public static ArrayList<Question> getQuestionList() {
        questionList = new ArrayList<Question>();
        String query = "SELECT * FROM APP.QUESTIONS";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                String[] answers = rs.getString(3).split(";");
                int rightAnswer = rs.getInt(4);
                questionList.add(new Question(id, text, answers, rightAnswer));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return questionList;
    }

    // Šī metode ir nepieciešama, lai noskaidrotu jautājumu kārtas numurus.
    // Id nosaka, kurš jautājums pieder testam.
    public static int[] getQuestionIDList(int count) {
        getQuestionList();
        int lastID;
        if (questionList.size() != 0) {
            lastID = questionList.get(questionList.size() - 1).getId() + 1;
        } else {
            lastID = 1;
        }
        int[] questionIDList = new int[count];
        for (int i = 0; i < count; i++) {
            questionIDList[i] = lastID + i;
        }
        return questionIDList;
    }

    // Konkrēta jautājuma meklēšana pēc id
    public static Question getQuestion(int id) {
        getQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getId() == id) {
                return questionList.get(i);
            }
        }
        return null;
    }
}

/*
CREATE TABLE "QUESTIONS"
(
    "ID" INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    "TEXT" VARCHAR(50),
    "ANSWERS" VARCHAR(400),
    "RIGHTANSWER" INT
)
*/
