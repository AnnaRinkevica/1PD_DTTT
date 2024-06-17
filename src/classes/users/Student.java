/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes.users;

import static PD1_Rinkevica.PD1_Rinkevica.jframe;
import classes.tests.Test;

/**
 *
 * @author anna.rinkevica.PR-21
 * @since 17.06.2024
 *
 */
public class Student extends User {
    
    public Test test;

    public Student(int id, String login, String password, String firstName, String lastName, String email, boolean rights, int[] pointlist) {
        super(id, login, password, firstName, lastName, email, rights, pointlist);
    }

    @Override
    public void startTest() {
        if (!jframe.jList1.isSelectionEmpty()) {
            for (int i = 0; i < jframe.user.getTestList().length; i++) {
                if (jframe.jList1.getSelectedValue().equals(jframe.user.getTestList()[i].getName())) {
                    test = jframe.user.getTestList()[i];
                    System.out.println(jframe.user.getTestList()[i].getName());
                }
            }
            jframe.radioMemory = new boolean[test.getQuestionList().length][4];
            jframe.questionID = 0;
            openQuestion(jframe.questionID);
            jframe.QuestionPattern.setBounds(0, 0, 420, 339);
            jframe.QuestionPattern.setLocationRelativeTo(null);
            jframe.QuestionPattern.setVisible(true);
        }
    }

    // Kad skolotājs ir pievienojis testu, mums ir jāzina jautājumu un atbilžu saturs
    private void openQuestion(int questionID) {
        jframe.buttonGroup2.clearSelection();
        jframe.jLabel36.setText(test.getName());
        jframe.jLabel37.setText(test.getQuestionList()[questionID].getText());
        jframe.jRadioButton1.setText(test.getQuestionList()[questionID].getAnswers()[0]);
        jframe.jRadioButton2.setText(test.getQuestionList()[questionID].getAnswers()[1]);
        jframe.jRadioButton3.setText(test.getQuestionList()[questionID].getAnswers()[2]);
        jframe.jRadioButton4.setText(test.getQuestionList()[questionID].getAnswers()[3]);
        jframe.jRadioButton1.setSelected(jframe.radioMemory[questionID][0]);
        jframe.jRadioButton2.setSelected(jframe.radioMemory[questionID][1]);
        jframe.jRadioButton3.setSelected(jframe.radioMemory[questionID][2]);
        jframe.jRadioButton4.setSelected(jframe.radioMemory[questionID][3]);
    }

    // Testa rezultātu rādīšana
    @Override
    public void showStatus() {
        if (!jframe.jList1.isSelectionEmpty()) {
            for (int i = 0; i < jframe.user.getTestList().length; i++) {
                if (jframe.jList1.getSelectedValue().equals(jframe.user.getTestList()[i].getName())) {
                    jframe.Stat.setBounds(0, 0, 370, 238);
                    jframe.Stat.setLocationRelativeTo(null);
                    jframe.Stat.setVisible(true);
                    jframe.jLabel21.setText(jframe.user.getTestList()[i].getId() + "");
                    jframe.jLabel22.setText(jframe.user.getTestList()[i].getQuestionidlist().length + "");
                    jframe.jLabel25.setText(jframe.user.getTestList()[i].getQuestionidlist().length + "");
                }
            }
        }
    }

    // Metode pārejai uz nākamo jautājumu un rezultātu aprēķināšanai
    @Override
    public void nextQuestion() {
        if (jframe.questionID + 1 < test.getQuestionList().length && jframe.questionID >= 0) {
            jframe.radioMemory[jframe.questionID] = new boolean[]{jframe.jRadioButton1.isSelected(), jframe.jRadioButton2.isSelected(), jframe.jRadioButton3.isSelected(), jframe.jRadioButton4.isSelected()};
            jframe.questionID++;
            openQuestion(jframe.questionID);
        } else {
            jframe.radioMemory[jframe.questionID] = new boolean[]{jframe.jRadioButton1.isSelected(), jframe.jRadioButton2.isSelected(), jframe.jRadioButton3.isSelected(), jframe.jRadioButton4.isSelected()};
            int pointsum = 0;
            for (int i = 0; i < jframe.radioMemory.length; i++) {
                if (jframe.radioMemory[i][test.getQuestionList()[i].getRightAnswers()]) {
                    pointsum++;
                }
            }
            jframe.jLabel21.setText(test.getId() + "");
            jframe.jLabel22.setText(test.getQuestionList().length + "");
            jframe.jLabel23.setText(pointsum + "");
            jframe.jLabel25.setText(test.getQuestionList().length + "");
            double procent = ((double) (pointsum) / (double) (test.getQuestionList().length)) * 100;
            jframe.jLabel26.setText(procent + "");
            if (procent < 10) {
                jframe.jLabel27.setText("F");
            } else if (10 <= procent && procent < 20) {
                jframe.jLabel27.setText("E");
            } else if (20 <= procent && procent < 30) {
                jframe.jLabel27.setText("D");
            } else if (30 <= procent && procent < 40) {
                jframe.jLabel27.setText("D+");
            } else if (40 <= procent && procent < 50) {
                jframe.jLabel27.setText("C-");
            } else if (50 <= procent && procent < 60) {
                jframe.jLabel27.setText("C");
            } else if (60 <= procent && procent < 70) {
                jframe.jLabel27.setText("B-");
            } else if (70 <= procent && procent < 80) {
                jframe.jLabel27.setText("B");
            } else if (80 <= procent && procent < 90) {
                jframe.jLabel27.setText("A-");
            } else if (90 <= procent) {
                jframe.jLabel27.setText("A");
            }
            jframe.Stat.setBounds(0, 0, 370, 238);
            jframe.Stat.setLocationRelativeTo(null);
            jframe.Stat.setVisible(true);
            jframe.QuestionPattern.dispose();
        }
    }

    // Metode pārejai uz iepriekšējo jautājumu
    @Override
    public void previousQuestion() {
        if (jframe.questionID < test.getQuestionList().length && jframe.questionID - 1 >= 0) {
            jframe.radioMemory[jframe.questionID] = new boolean[]{jframe.jRadioButton1.isSelected(), jframe.jRadioButton2.isSelected(), jframe.jRadioButton3.isSelected(), jframe.jRadioButton4.isSelected()};
            jframe.questionID--;
            openQuestion(jframe.questionID);
        } else {
            jframe.TestList.setBounds(0, 0, 620, 296);
            jframe.TestList.setLocationRelativeTo(null);
            jframe.TestList.setVisible(true);
            jframe.QuestionPattern.dispose();
        }
    }

    private void setRights(boolean rights) {
        this.rights = rights;
    }
}
