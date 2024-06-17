/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.tests;

/**
 *
 * @author anna.rinkevica.PR-21
 * @since 17.06.2024
 *
 */
public class Question {
    
    private int id;
    private String text;
    private String[] answers;
    private int rightAnswers;

    public Question(int id, String text, String[] answers, int rightAnswers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
        this.rightAnswers = rightAnswers;
    }
    
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }
    
}
