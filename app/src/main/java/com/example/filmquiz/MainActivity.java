package com.example.filmquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmquiz.pojos.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btntrue;
    private Button btnfalse;
    private TextView tvquestion;
    private TextView tvscore;
    private Button btnagain;
    private int score = 0;
    private int index = 0;
    private List<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère les éléments de la vue
        btntrue = findViewById(R.id.btntrue);
        btnfalse = findViewById(R.id.btnfalse);
        tvquestion = findViewById(R.id.tvquestion);
        tvscore = findViewById(R.id.tvscore);
        btnagain = findViewById(R.id.btnagain);

        Question question1 = new Question(getString(R.string.question_ai), true);
        Question question2 = new Question(getString(R.string.question_taxi_driver), false);
        Question question3 = new Question(getString(R.string.question_2001), true);
        Question question4 = new Question(getString(R.string.question_reservoir_dogs), false);
        Question question5 = new Question(getString(R.string.question_citizen_kane), true);

        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        questionList.add(question4);
        questionList.add(question5);

        //On affiche la première question
        tvquestion.setText(questionList.get(index).getText());
        tvscore.setText(String.format("Score : %d points", score));

        // On gère l'evenement
        btntrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                // Méthode pour vérifier la réponse
                checkQuestion(true);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        btnfalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "";
                // Méthode pour vérifier la réponse
                checkQuestion(false);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        // On gère le bouton rejouer
        btnagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // met à 0 l'index
                index = 0;
                // met à 0 le score
                score = 0;
                // Remontre la première question
                tvquestion.setText(questionList.get(index).getText());
                tvscore.setText(String.format("Score : %d points", score));
                btnagain.setVisibility(View.INVISIBLE);
            }
        });
    }

    // En cas de succès
    public String checkQuestion(boolean response) {
        if (questionList.get(index).isAnswer() == response) {
            if (index < questionList.size() - 1) {
                score++;
                showText();
                String text = "Réponse juste !";
                return text;
            } else {
                btnagain.setVisibility(View.VISIBLE);
                return null;
            }
        } else {
            if (index < questionList.size() -1) {
                showText();
                String text = "Réponse fausse...";
                return text;
            } else {
                btnagain.setVisibility(View.VISIBLE);
                return null;
            }
        }
    };
    private void showText() {
        tvscore.setText(String.format("Score : %d points", score));
        index++;
        tvquestion.setText(questionList.get(index).getText());
    }
}