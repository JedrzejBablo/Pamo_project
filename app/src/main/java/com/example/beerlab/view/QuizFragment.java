package com.example.beerlab.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.beerlab.Questions;
import com.example.beerlab.R;
import com.example.beerlab.activity.QuizActivity;

public class QuizFragment extends Fragment {
    private Questions mQuestions = new Questions();

    private String mAnswer;
    public int mScore = 0;
    private int mQuestionLength = mQuestions.mQuestions.length;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);

        Button answer1, answer2, answer3, answer4;

        TextView score, question;

        int r;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final View view = inflater.inflate(R.layout.fragment_quiz,container,false)

            r = 0;

            answer1 = (Button) view.findViewById(R.id.answer1);
            answer2 = (Button) view.findViewById(R.id.answer2);
            answer3 = (Button) view.findViewById(R.id.answer3);
            answer4 = (Button) view.findViewById(R.id.answer4);

            score = (TextView) view.findViewById(R.id.score);
            question = (TextView) view.findViewById(R.id.question);

            score.setText("Wynik: " + mScore);

            updateQuestion(r);

            answer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer1.getText() == mAnswer) {
                        mScore++;
                        score.setText("Wynik : " + mScore);
                        r++;
                        updateQuestion(r);
                    } else {
                        gameOver();
                    }
                }
            });

            answer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer2.getText() == mAnswer) {
                        mScore++;
                        score.setText("Wynik : " + mScore);
                        r++;
                        updateQuestion(r);
                    } else {
                        gameOver();
                    }
                }
            });

            answer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer3.getText() == mAnswer) {
                        mScore++;
                        score.setText("Wynik : " + mScore);
                        r++;
                        updateQuestion(r);
                    } else {
                        gameOver();
                    }
                }
            });

            answer4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (answer4.getText() == mAnswer) {
                        mScore++;
                        score.setText("Wynik : " + mScore);
                        r++;
                        updateQuestion(r);
                    } else {
                        gameOver();
                    }
                }
            });
        }

        private void updateQuestion(int num) {
            if (num > 5) {
                endTheGame();
            } else {
                question.setText(mQuestions.getQuestion(num));
                answer1.setText(mQuestions.getChoice1(num));
                answer2.setText(mQuestions.getChoice2(num));
                answer3.setText(mQuestions.getChoice3(num));
                answer4.setText(mQuestions.getChoice4(num));

                mAnswer = mQuestions.getCorrectAnwer(num);
            }
        }

        private void gameOver() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
            alertDialogBuilder
                    .setMessage("Zła odpowiedz udało Ci się uzyskać  " + mScore + " " + "punktów.")
                    .setCancelable(false)
                    .setPositiveButton("Zacznij od nowa",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                                    finish();
                                }
                            })

                    .setNegativeButton("Wyjscie",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(QuizActivity.this, "", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        private void endTheGame() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
            alertDialogBuilder
                    .setMessage("Gratulacje uzyskałeś  " + mScore + " " + "punktów.")
                    .setCancelable(false)
                    .setPositiveButton("Zacznij od nowa",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                                    finish();
                                }
                            })

                    .setNegativeButton("Wyjscie",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(QuizActivity.this, "", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }
}
