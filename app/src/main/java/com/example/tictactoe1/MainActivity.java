package com.example.tictactoe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editName1 = findViewById(R.id.nameText1);
        EditText editName2 = findViewById(R.id.nameText2);
        Button play = findViewById(R.id.playButton);
        play.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("name1", editName1.getText() + "");
            intent.putExtra("name2", editName2.getText() + "");
            startActivity(intent);
        });
    }
}