package com.moutamid.dantlicorp.Activities.OnBoarding;

public class OnBoardingModel {
    String text, textMain;
    int vector;

    public OnBoardingModel(String text, String textMain, int vector) {
        this.text = text;
        this.textMain = textMain;
        this.vector = vector;
    }


    public String getText() {
        return text;
    }

    public String getTextMain() {
        return textMain;
    }

    public int getVector() {
        return vector;
    }
}
