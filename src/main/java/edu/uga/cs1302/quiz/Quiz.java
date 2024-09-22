package edu.uga.cs1302.quiz;

import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Quiz implements Serializable {
    private List<Question> question;
    private int score;

    //Constructor
    public Quiz(List<Country> countries) {
	this.question = new ArrayList<>();
	initializeQuiz(countries);
    }

    private void initializeQuiz(List<Country> countries) {
	Random random = new Random();
	List<Country> selectedCountry = new ArrayList<>();

	//Select 6 countries randomly
	while (selectedCountry.size() < 6) {
	    Country country = countries.get(random.nextInt(countries.size()));
	    if (!selectedCountry.contains(country)) {
		selectedCountry.add(country);
	    }
	}

	//Question for each selected country
	for (Country country : selectedCountry) {
	    List<String> choices = generateChoice(country, countries);
	    Question question = new Question(country, choices, country.getContinent());
	    this.question.add(question);
	}
    }

    private List<String> generateChoice(Country country, List<Country> countries) {
	Random random = new Random();
	List<String> choices = new ArrayList<>();
	String correctContinent = country.getContinent();

	//Add correct continent as one of the choices
	choices.add(correctContinent);

	//Add 2 more random continents as choices (avoid duplicate)
	while (choices.size() < 3) {
	    int randomIndex = random.nextInt(countries.size());
	    String continent = countries.get(randomIndex).getContinent();
	    if (!choices.contains(continent)&& !continent.equals(correctContinent)) {
		choices.add(continent);
	    }
        }

	//Shuffle choices to make 3 possible answers are shown in random order, for each question
	Collections.shuffle(choices);
	return choices;
    }

    public List<Question> getQuestions() {
	return question;
    }

    //Calculate score
    public void calculateScore(List<String> answer) {
	score = 0;
	for (int i = 0; i < question.size(); i++) {
	    if (question.get(i).getCorrectAnswer().equals(answer.get(i))) {
		score++;
	    }
	}
    }

    public int getScore() {
	return score;
    } 

    //Display quiz questions
    @Override
    public String toString() {
	String result = "";
	for (int i = 0; i < question.size(); i++) {
	    result += "Question " + (i + 1) + ": " + question.get(i).getCountry().getName() + "\n";
	    result += "Options: " + String.join(", ", question.get(i).getChoices()) + "\n";
	}
        return result;
    }
}
	    
	    
	
	    
	    
	    

    
       
       
