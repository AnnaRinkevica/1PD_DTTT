/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.tests;

import classes.DB_Question;

/**
 *
 * @author anna.rinkevica.PR-21
 * @since 17.06.2024
 *
 */
public class Test {

    private int id;
    private String name;
    private int[] questionidlist;
    private Question[] questionList;
    private boolean active;

    public Test(int id, String name, int[] questionidlist, boolean active) {
        this.id = id;
        this.name = name;
        this.questionidlist = questionidlist;
        questionList = new Question[questionidlist.length];
        for (int i = 0; i < questionidlist.length; i++) {
            questionList[i] = DB_Question.getQuestion(questionidlist[i]);
        }
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[] getQuestionidlist() {
        return questionidlist;
    }

    public Question[] getQuestionList() {
        return questionList;
    }
    
}
 