package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "correctAnswer";
    public static final int REQUEST_CODE_PROMPT = 0;
    public boolean answerWasShown;
    private int correctAnswers=0;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;

    private int correctAns = R.string.correct_answer;

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    private Question[] questions = new Question[]{
            new Question(R.string.q_jd,false),
            new Question(R.string.q_job,false),
            new Question(R.string.q_hit,true),
            new Question(R.string.q_cpt,true),
            new Question(R.string.q_idk,true)
    };
    private int currentIndex = 0;


    private void checkAsnwerCorrectness (boolean userAnswer) {
        boolean correctAsnwer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        String result = null;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            result = "";
            if (userAnswer == correctAsnwer) {
                resultMessageId = R.string.correct_answer;
                correctAnswers += 1;
                result = getString(resultMessageId) + " " + correctAnswers;

            } else {
                resultMessageId = R.string.incorrect_answer;
                result = getString(resultMessageId);
            }

        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Start","ZACZĘŁO SIĘ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Pause","ZAWIESIŁO SIĘ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Resume","ZNOWU LECI");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy","ZNISZCZYŁO SIĘ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Stop","STOP");
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d("QUIZ_TAG", "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null)
        {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        setContentView(R.layout.activity_main);
        promptButton = findViewById(R.id.promptButton);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick (View v){
               checkAsnwerCorrectness(true);
           }
        });
        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                checkAsnwerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                currentIndex = (currentIndex+1)%questions.length;
                boolean answerWasShown = false;
                setNextQuestion();
            }
        });
        promptButton.setOnClickListener((v)->{
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);

        });






        setNextQuestion();
    }

}
