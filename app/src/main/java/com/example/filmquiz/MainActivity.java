package com.example.filmquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmquiz.pojos.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_SCORE = "score";
    private static final String INDEX_QUESTION = "questionEnCours";
    private static final String BTN_AGAIN_VISIBILITY = "againVisibility";
    public static final String KEY_QUESTION = "question";

    private Button btntrue;
    private Button btnfalse;
    private TextView tvquestion;
    private TextView tvscore;
    private Button btnagain;
    private int score = 0;
    private int index = 0;
    private List<Question> questionList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "OnCreate() called");

        // On récupère les éléments de la vue
        btntrue = findViewById(R.id.btntrue);
        btnfalse = findViewById(R.id.btnfalse);
        tvquestion = findViewById(R.id.tvquestion);
        tvscore = findViewById(R.id.tvscore);
        btnagain = findViewById(R.id.btnagain);

        // On créait le context
        context = getApplicationContext();

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

        // Récupération de la question en cours du Bundle
        if(savedInstanceState != null){
            index = savedInstanceState.getInt(INDEX_QUESTION);
            score = savedInstanceState.getInt(KEY_SCORE );
            btnagain.setVisibility(savedInstanceState.getInt(BTN_AGAIN_VISIBILITY));
        }

        //On affiche la première question
        tvquestion.setText(questionList.get(index).getText());
        tvscore.setText(String.format("Score : %d points", score));

        // On gère l'evenement
        btntrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(INDEX_QUESTION, index);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(BTN_AGAIN_VISIBILITY, btnagain.getVisibility());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // On créait le menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // effectue une action suivant l'item sélectionné
        // on test avec un switch l'id de l'item
        switch (item.getItemId()){
            case R.id.cheat:
                // Créer un Intent pour ensuite lancer CheatActivity
                Intent intent = new Intent(context, CheatActivity.class);

                //on ajouter des données dans le intent pour y accéder dans la nouvelle activity
                intent.putExtra(KEY_QUESTION, questionList.get(index));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume() called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause() called");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "OnPause() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "OnRestart() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OnDestroy() called");

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