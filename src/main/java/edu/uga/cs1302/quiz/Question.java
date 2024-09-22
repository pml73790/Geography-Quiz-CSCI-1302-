package edu.uga.cs1302.quiz;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private Country country;
    private List<String> choices;
    private String correctAnswer;

    //Constructor
    public Question(Country country, List<String> choices, String correctAnswer) {
	this.country = country;
	this.choices = choices;
	this.correctAnswer = correctAnswer;
    }

    public Country getCountry() {
	return country;
    }

    public List<String> getChoices() {
	return choices;
    }

    public String getCorrectAnswer() {
	return correctAnswer;
    }
}
	 
	
