package com.example.tictactoe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private boolean isO;
    private char [][] board;
    private int count;
    private ImageView [][] boxes;
    private LinearLayout playerTexts, resultsLayout;
    private String name1, name2;
    private TextView player1;
    private TextView player2;
    private boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        name1 = "Player 1";
        name2 = "Player 2";
        if(extras != null) {
            name1 = extras.getString("name1");
            name2 = extras.getString("name2");
        }

        boxes = new ImageView[][] {{findViewById(R.id.box1), findViewById(R.id.box2), findViewById(R.id.box3)},
                {findViewById(R.id.box4), findViewById(R.id.box5), findViewById(R.id.box6)},
                {findViewById(R.id.box7), findViewById(R.id.box8), findViewById(R.id.box9)}};
        board = new char[3][3];
        playerTexts = findViewById(R.id.playerTexts);
        resultsLayout = findViewById(R.id.resultsLayout);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        /*
        start with this, replace with method call once reset() is created
        player1.setText(name1 + " (X)");
        player2.setText(name2 + " (O)");
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
                boxes[i][j].setImageResource(R.drawable.blank);
                boxes[i][j].setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        count = 0;
         */
        reset();

        for (int i = 0; i < boxes.length; i++) {
            for(int j = 0; j < boxes[i].length; j++) {
                ImageView box = boxes[i][j];
                final int x = i; // these are needed because of a weird rule
                final int y = j;
                box.setOnClickListener(v -> {
                    if(board[x][y] == ' ' && !gameOver) {
                        if (isO) {
                            board[x][y] = 'O';
                            box.setImageResource(R.drawable.o);
                            /* isO = false;
                            start with just this, after creating changePlayer(), replace
                            with method call
                             */
                            changePlayer(true);
                        }
                        else {
                            board[x][y] = 'X';
                            box.setImageResource(R.drawable.x);
                            /* isO = true;
                            start with just this, after creating changePlayer(), replace
                            with method call
                             */
                            changePlayer(false);
                        }
                        count++;
                    }

                    char winner = checkWin();
                    if(count == 9 || winner != ' ') {
                        gameOver = true;
                        playerTexts.setVisibility(View.GONE);
                        resultsLayout.setVisibility(View.VISIBLE);
                        TextView winText = findViewById(R.id.winText);
                        if(winner == 'X') winText.setText(name1 + " won!");
                        else if(winner == 'O') winText.setText(name2 + " won!");
                        else winText.setText("The game was a tie.");
                        String temp = name1; // swap who is player 1 and player 2
                        name1 = name2;
                        name2 = temp;
                    }
                });
            }
        }

        Button playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(v -> {
           reset();
           gameOver = false;
        });

        Button quitButton = findViewById(R.id.quit);
        quitButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void changePlayer(boolean nextIsX) {
        ImageView arrow1 = findViewById(R.id.arrow1);
        ImageView arrow2 = findViewById(R.id.arrow2);

        if(nextIsX) {
            isO = false;
            arrow2.setVisibility(View.GONE);
            arrow1.setVisibility(View.VISIBLE);
        }
        else {
            isO = true;
            arrow1.setVisibility(View.GONE);
            arrow2.setVisibility(View.VISIBLE);
        }
    }

    public char checkWin() {
        //check for horizontal win
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                for(int j = 0; j < 3; j++)
                    boxes[i][j].setBackgroundColor(getResources().getColor(R.color.yellow));
                return board[i][0];
            }
        }

        //check for vertical win
        for(int i = 0; i < 3; i++) {
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                for(int j = 0; j < 3; j++)
                    boxes[j][i].setBackgroundColor(getResources().getColor(R.color.yellow));
                return board[0][i];
            }
        }

        //check for diagonal win
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            for(int i = 0; i < 3; i++)
                boxes[i][i].setBackgroundColor(getResources().getColor(R.color.yellow));
            return board[0][0];
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            for(int i = 0; i < 3; i++)
                boxes[i][2-i].setBackgroundColor(getResources().getColor(R.color.yellow));
            return board[0][2];
        }

        return ' ';
    }

    public void reset() {
        playerTexts.setVisibility(View.VISIBLE);
        player1.setText(name1 + " (X)");
        player2.setText(name2 + " (O)");
        resultsLayout.setVisibility(View.GONE);
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
                boxes[i][j].setImageResource(R.drawable.blank);
                boxes[i][j].setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        count = 0;
        changePlayer(true);
    }
}