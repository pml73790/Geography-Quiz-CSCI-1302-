package edu.uga.cs1302.quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizResult implements Serializable {
    private List<String> quizScores;

    //Constructor
    public QuizResult() {
	this.quizScores = new ArrayList<>();
    }

    public List<String> getQuizScores() {
	return quizScores;
    }
    
    //Add score to the beginning of the list
    public void addQuizScore(String quizScore) {
	quizScores.add(0, quizScore);
    }

    @Override
    public String toString() {
	String result = "Quiz result:\n";
	for (String score : quizScores) {
	    result += score + "\n";
	}
	return result;
    }
}
