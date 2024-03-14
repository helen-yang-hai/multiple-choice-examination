package com.example.labtest2;

public class Question {
    private int questionNum;
    private String questionDesc;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String rightAnswer;

    public Question() {
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {this.questionNum = questionNum;}

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionNum=" + questionNum +
                ", questionDesc='" + questionDesc + '\'' +
                ", answerA='" + optionA + '\'' +
                ", answerB='" + optionB + '\'' +
                ", answerC='" + optionC + '\'' +
                ", answerD='" + optionD + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                '}';
    }
}

