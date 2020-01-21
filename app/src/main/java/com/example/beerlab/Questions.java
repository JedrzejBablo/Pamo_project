package com.example.beerlab;

public class Questions {
    public String mQuestions[] = {
            "Prawidłowe ciśnienie u człowieka wynosi?",
            "Jakie powinno być prawidłowe tętno człowieka?",
            "Ile wynosi prawidłowy poziom cholesterolu?",
            "Który hormon występuje w trzustce?",
            "Ile wynosi prawidłowa tolerancja glukozy w teście obciążeniowym?",
            "Dializa jest wykonywana na:"



    };

    private String mChoices[][] = {
            {"120/80", "140/90", "80/120", "90/140"},
            {"90-120 skurczów/min", "60-80 skurczów/min", "40-60 skurczów/min", "120-150 skurczów/min"},
            {"do 120 mg/dl", "do 210 mg/dl", "do 280 mg/dl", "do 200 mg/dl"},
            {"adrenalina", "insulina", "oksytocyna", "testosteron"},
            {"od 140 do 200 mg/dL", "od 200 do 300 mg/dL", "poniżej 140 mg/dL", "powyżej 140 mg/dL"},
            {"nerkach", "trzustce", "sercu", "wątrobie"}



    };

    private String mCorrectAnswer[] = {"120/80", "60-80 skurczów/min", "do 200 mg/dl", "insulina", "poniżej 140 mg/dL", "nerkach"};

    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a) {
        String choice = mChoices[a][0];
        return choice;
    }

    public String getChoice2(int a) {
        String choice = mChoices[a][1];
        return choice;
    }

    public String getChoice3(int a) {
        String choice = mChoices[a][2];
        return choice;
    }

    public String getChoice4(int a) {
        String choice = mChoices[a][3];
        return choice;
    }

    public String getCorrectAnwer(int a) {
        String answer = mCorrectAnswer[a];
        return answer;
    }
}