package com.example.filmquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.filmquiz.pojos.Question;

public class CheatActivity extends AppCompatActivity {

    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // récupère la barre d'action
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        tvResponse = findViewById(R.id.tvReponse);


        // On récupèr l'intent qui a appelé cette activitu
        Intent intent = getIntent();

        // récupère la question qui est dans l'intent
        Question question = (Question) intent.getSerializableExtra(MainActivity.KEY_QUESTION);

        tvResponse.setText(String.format("%s : %s", question.getId(), question.isAnswer()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        // termine une activité
        finish();
        return super.onSupportNavigateUp();
    }
}