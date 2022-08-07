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
        reset();

        for (int i = 0; i < boxes.length; i++) {
            for(int j = 0; j < boxes[i].length; j++) {
                ImageView box = boxes[i][j];
                final int x = i;
                final int y = j;
                box.setOnClickListener(v -> {
                    if(board[x][y] == ' ') {
                        if (isO) {
                            board[x][y] = 'O';
                            box.setImageResource(R.drawable.o);
                            changePlayer(true);
                        }
                        else {
                            board[x][y] = 'X';
                            box.setImageResource(R.drawable.x);
                            changePlayer(false);
                        }
                        count++;
                    }

                    char winner = checkWin();
                    if(count == 9 || winner != ' ') {
                        playerTexts.setVisibility(View.GONE);
                        resultsLayout.setVisibility(View.VISIBLE);
                        TextView winText = findViewById(R.id.winText);
                        String winnerStr = "";
                        if(winner == 'X') winnerStr = name1 + " won!";
                        else if(winner == 'O') winnerStr = name2 + " won!";
                        else winnerStr = "The game was a tie.";
                        winText.setText(winnerStr);
                        String temp = name1;
                        name1 = name2;
                        name2 = temp;
                    }
                });
            }
        }

        Button playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(v -> {
           reset();
        });
    }

    public void changePlayer(boolean nextIsX) {
        ImageView arrow1 = findViewById(R.id.arrow1);
        ImageView arrow2 = findViewById(R.id.arrow2);

        if(nextIsX) {
            isO = false;
            player1.setTypeface(null, Typeface.BOLD);
            player2.setTypeface(null, Typeface.NORMAL);
            arrow2.setVisibility(View.GONE);
            arrow1.setVisibility(View.VISIBLE);
        }
        else {
            isO = true;
            player2.setTypeface(null, Typeface.BOLD);
            player1.setTypeface(null, Typeface.NORMAL);
            arrow1.setVisibility(View.GONE);
            arrow2.setVisibility(View.VISIBLE);
        }
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
}