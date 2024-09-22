package edu.uga.cs1302.quiz;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GeographyQuiz extends Application {
    private Quiz quiz;
    private Label questionLabel;
    private RadioButton[] options;
    private ToggleGroup optionsGroup;
    private Label scoreLabel;
    private int currentQuestionIndex = 0;
    private List<String> userAnswers; //Store user's answers
    private Stage primaryStage;
    private QuizResult quizResult;

    @Override
    public void start(Stage primaryStage) {
	this.primaryStage = primaryStage;
        quizResult = loadQuizResults();
	userAnswers = new ArrayList<>();

	VBox root = new VBox(10);
	root.setPadding(new Insets(15));

	questionLabel = new Label();
	root.getChildren().add(questionLabel);

	optionsGroup = new ToggleGroup();
	options = new RadioButton[3];
	for (int i = 0; i < options.length; i++) {
	    options[i] = new RadioButton();
	    options[i].setToggleGroup(optionsGroup);
	    root.getChildren().add(options[i]);
	}

	Button nextButton = new Button("Next");
	nextButton.setOnAction(e -> handleNextButton());
	root.getChildren().add(nextButton);

	Button finishButton = new Button("Finish");
	finishButton.setOnAction(e -> handleFinishButton());
	root.getChildren().add(finishButton);

	Button viewPastResultsButton = new Button("View Past Results");
	viewPastResultsButton.setOnAction(e -> showPastResults());
	root.getChildren().add(viewPastResultsButton);

	Button helpButton = new Button("Help");
	helpButton.setOnAction(e -> showHelp());
	root.getChildren().add(helpButton);

	Button quitButton = new Button("Quit");
	quitButton.setOnAction(e -> quitApplication());
	root.getChildren().add(quitButton);

	initializeQuiz();
	Scene scene = new Scene(root, 400, 300);
	primaryStage.setTitle("Geography Quiz");
	primaryStage.setScene(scene);
	primaryStage.show();
    }

    private void initializeQuiz() {
	QuestionCollection questionCollection = new QuestionCollection();
	quiz = new Quiz(questionCollection.getCountries());
	userAnswers.clear();
	currentQuestionIndex = 0;
	updateQuestion();
    }

    private void updateQuestion() {
	if (currentQuestionIndex < quiz.getQuestions().size()) {
	    Question question = quiz.getQuestions().get(currentQuestionIndex);
	    questionLabel.setText("Which continent is " + question.getCountry().getName() + " in?");

	    for (int i = 0; i < options.length; i++) {
		if (i < question.getChoices().size()) {
		    options[i].setText(question.getChoices().get(i));
		} else {
		    options[i].setText("");
		}
	    }
	}
    }

    private void handleNextButton() {
	RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
	if (selectedOption != null) {
	    String selectedAnswer = selectedOption.getText();
	    userAnswers.add(selectedAnswer); //Store the answer

	    currentQuestionIndex++;
	    if (currentQuestionIndex < quiz.getQuestions().size()) {
		updateQuestion();
	    } else {
		handleFinishButton();
	    }
	} else {
	    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an option.");
	    alert.showAndWait();
	}
    }

    private void handleFinishButton() {
	quiz.calculateScore(userAnswers);

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	String date = LocalDateTime.now().format(formatter);
	String result = "Your score is: " + quiz.getScore() + " out of " + quiz.getQuestions().size() + " (Date: " + date + ")";
	quizResult.addQuizScore(result);

	saveQuizResults(quizResult);

	//Display result after a delay
	Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(3), e -> showFinalResult(result)));
	timeLine.play();
    }

    private void showFinalResult(String result) {
	Stage resultStage = new Stage();
	VBox root = new VBox(10);
	root.setPadding(new Insets(15));

	Label resultLabel = new Label(result);
	Button closeButton = new Button("Close");
	closeButton.setOnAction(e -> resultStage.close());

	root.getChildren().addAll(resultLabel, closeButton);

	Scene scene = new Scene(root, 300, 150);
	resultStage.setTitle("Quiz Result");
	resultStage.setScene(scene);
	resultStage.show();
    }

    private void saveQuizResults(QuizResult quizResult) {
	try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("quizzes.dat"))) {
	    objectOutputStream.writeObject(quizResult);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private QuizResult loadQuizResults() {
	QuizResult quizResult = new QuizResult();
	try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("quizzes.dat"))) {
	    quizResult = (QuizResult) objectInputStream.readObject();
	} catch (IOException | ClassNotFoundException e) {
	    e.printStackTrace();
	}

	return quizResult;
    }

    private void showPastResults() {
	Stage resultsStage = new Stage();
	VBox root = new VBox(10);
	root.setPadding(new Insets(15));

	String pastResultsText = "Past Quiz Results:\n";
	for (String result : quizResult.getQuizScores()) {
	    pastResultsText += result + "\n";
	}

	Label resultsLabel = new Label(pastResultsText);
	Button closeButton = new Button("Close");
	closeButton.setOnAction(e -> resultsStage.close());

	root.getChildren().addAll(resultsLabel, closeButton);

	Scene scene = new Scene(root, 300, 250);
	resultsStage.setTitle("Past Quiz Results");
	resultsStage.setScene(scene);
	resultsStage.show();
    }

    private void showHelp() {
	Stage helpStage = new Stage();
	VBox root = new VBox(10);
	root.setPadding(new Insets(15));

	Label helpLabel = new Label("Instruction:\n" +
				    "1. Read each question carefully.\n" +
				    "2. Select the correct option from the given choices.\n" +
				    "3. Click 'Next' to proceed to the next question.\n" +
				    "4. Click 'Finish' to complete the quiz and see what is your results.");

	Button closeButton = new Button("Close");
	closeButton.setOnAction(e -> helpStage.close());

	root.getChildren().addAll(helpLabel, closeButton);

	Scene scene = new Scene(root, 300, 250);
	helpStage.setTitle("Help");
	helpStage.setScene(scene);
	helpStage.show();
    }
				      
    private void quitApplication() {
	Platform.exit();
    }
	    
    public static void main(String[] args) {
	launch(args);
    }
}

	    

	
			
			   
