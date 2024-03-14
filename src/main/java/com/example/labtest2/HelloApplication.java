package com.example.labtest2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class HelloApplication extends Application {

  @Override
  public void start(Stage stage) throws FileNotFoundException {

    GridPane grid = new GridPane();
    Scene scene = new Scene(grid);
    grid.setHgap(15);
    grid.setVgap(15);
    grid.setPadding(new Insets(15));

    Label examDescription = new Label("You should answer the following questions. Select the single answer from the four choice.");
    examDescription.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 16));

    Label stuNameL = new Label("Student's Name:");
    TextField stuNameTF = new TextField();

    Label questionLabel = new Label("Questions");
    questionLabel.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 16));


    VBox questionsBox = new VBox();
    ToggleGroup[] groups = new ToggleGroup[5];

    //Random pick 5 questions and then display in the VBox questionsBox
    Question[] questions = getAllQuestions();
    int[] set = getSet(questions.length);
    int count = 1;
    String[] answers = new String[5];
    for (int i = 0; i < 20; i++) {
      if (i == set[0] || i == set[1] || i == set[2] || i == set[3] || i == set[4]) {
        final ToggleGroup group = new ToggleGroup();
        groups[count - 1] = group;
        //display the question
        String questionDescriptionText = count + "." + questions[i].getQuestionDesc();
        Label questionDescription = new Label(questionDescriptionText);
        questionsBox.getChildren().add(questionDescription);

        //get each question's right answer and save in an array
        answers[count- 1] = questions[i].getRightAnswer();

        //display 4 options
        RadioButton rb1 = new RadioButton(questions[i].getOptionA());
        rb1.setToggleGroup(group);
        rb1.setUserData(questions[i].getOptionA().split("\\)")[0]);
        rb1.setPadding(new Insets(2, 2, 2, 20));
        questionsBox.getChildren().add(rb1);
        RadioButton rb2 = new RadioButton(questions[i].getOptionB());
        rb2.setToggleGroup(group);
        rb2.setUserData(questions[i].getOptionB().split("\\)")[0]);
        rb2.setPadding(new Insets(2, 2, 2, 20));
        questionsBox.getChildren().add(rb2);
        RadioButton rb3 = new RadioButton(questions[i].getOptionC());
        rb3.setToggleGroup(group);
        rb3.setUserData(questions[i].getOptionC().split("\\)")[0]);
        rb3.setPadding(new Insets(2, 2, 2, 20));
        questionsBox.getChildren().add(rb3);
        RadioButton rb4 = new RadioButton(questions[i].getOptionD());
        rb4.setToggleGroup(group);
        rb4.setUserData(questions[i].getOptionD().split("\\)")[0]);
        rb4.setPadding(new Insets(2, 2, 2, 20));
        questionsBox.getChildren().add(rb4);
        count++;
      }
    }

    HBox btns = new HBox(50);
    Button submitBtn = new Button("Submit");
    Button calBtn = new Button("Calculate");
    TextField gradeDisplay = new TextField();
    btns.getChildren().add(submitBtn);
    btns.getChildren().add(calBtn);
    btns.getChildren().add(gradeDisplay);


    HBox allAvg = new HBox();
    Label allCandidatesAvg = new Label("All Candidates Average Grade: ");
    TextField allCandidatesAvgTF = new TextField();
    allAvg.getChildren().add(allCandidatesAvg);
    allAvg.getChildren().add(allCandidatesAvgTF);

    Label error = new Label();
    error.setTextFill(Color.RED);

    grid.add(examDescription, 0, 0, 2, 1);
    grid.add(stuNameL, 0, 1);
    grid.add(stuNameTF, 1, 1);
    grid.add(questionLabel, 0, 2);
    grid.add(questionsBox, 0, 3, 2, 1);
    grid.add(btns, 0, 4, 2, 1);
    grid.add(allAvg, 0, 5, 2, 1);
    grid.add(error, 0, 6);
    error.setAlignment(Pos.CENTER);
    grid.setPadding(new Insets(10));

    stage.setTitle("Multiple Choice Exam");
    stage.setScene(scene);
    stage.setWidth(700);
    stage.setHeight(900);
    stage.show();

    //Calculate button action
    calBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if(!stuNameTF.getText().isEmpty() || !stuNameTF.getText().isBlank()){
          int gradeTotal = 0;
          for (int i = 0; i < 5; i++) {
            if (groups[i].getSelectedToggle() == null) {
              continue;
            } else if (groups[i].getSelectedToggle().getUserData().toString().equals(answers[i])) {
              gradeTotal += 20;
            } else {
              gradeTotal += -5;
            }
          }
          String finalGrade = String.valueOf(gradeTotal);
          gradeDisplay.setText(finalGrade);

          allCandidatesAvgTF.setText(calAllCandidateAvg());
          error.setText("");
        }else{
          error.setText("Name is required!");
        }
      }
    });

    //Submit button action, write name, selected answers and grade into file result.txt
    submitBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        String name = stuNameTF.getText();
        String result = "";
        for (int i = 0; i < 5; i++) {
          String selected = "X";
          if (groups[i].getSelectedToggle() != null) {
            selected = groups[i].getSelectedToggle().getUserData().toString();
          }
          result += selected;
        }
        String grade = gradeDisplay.getText();
        //grade needs to be calculated first, then can submit
        if (grade.length() == 0) {
          error.setText("Please calculate first, then submit again");
          return;
        }
        try {
          //write the file result.txt
          FileWriter myWriter = new FileWriter("result.txt", true);
          myWriter.write(name + "|" + result + "|" + grade + "\n");
          myWriter.close();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        allCandidatesAvgTF.setText(calAllCandidateAvg());
        error.setText("");
      }
    });

  }

  private String calAllCandidateAvg(){
    String avg = "";
    int allCandidateGrade = 0;
    int lines = 0;

    BufferedReader reader;
    try {
      //read the result.txt file to get all candidates' grades
      reader = new BufferedReader(new FileReader("result.txt"));
      String line = reader.readLine();
      while (line != null) {
        lines++;
        allCandidateGrade += Integer.parseInt(line.split("\\|")[2]);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //calculate all candidates average grade
    if(lines != 0){
      avg = String.format("%.2f",allCandidateGrade * 1.0 / lines);
    }
    System.out.printf(avg);
    return avg;
  }

  private Question[] getAllQuestions() {
    //read the file line by line and create instances
    int totalQuestion = 20;
    Question[] questions = new Question[totalQuestion];
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader("src/main/exam.txt"));
      for (int i = 0; i < totalQuestion; i++) {
        Question question = new Question();
        String[] arr = br.readLine().split("\\.");
        question.setQuestionNum(Integer.parseInt(arr[0]));
        question.setQuestionDesc(arr[1]);
        question.setOptionA(br.readLine());
        question.setOptionB(br.readLine());
        question.setOptionC(br.readLine());
        question.setOptionD(br.readLine());
        question.setRightAnswer(br.readLine());
        questions[i] = question;
      }
      br.close();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return questions;
  }

  // Randomly generate 5 numbers
  private int[] getSet(int questionNums) {
    return new Random().ints(0, questionNums)
            .distinct()
            .limit(5)
            .toArray();
  }

  public static void main(String[] args) {
    launch();
  }
}