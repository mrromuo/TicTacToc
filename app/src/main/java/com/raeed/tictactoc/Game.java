package com.raeed.tictactoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Random;

class Game {
    public static boolean[][] GAME_BOARD = new boolean[2][9];
    public static boolean PLAYER1_ST, PLAYER2_ST;
    Random random = new Random();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    void setbutoon (int but, int gamesequence, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        switch (but) {
            case R.id.bu1:
                GAME_BOARD[gamesequence][0] = true;
                editor.putBoolean("s0", true);
                editor.putInt("xo0", gamesequence);
                break;
            case R.id.bu2:
                GAME_BOARD[gamesequence][1] = true;
                editor.putBoolean("s1", true);
                editor.putInt("xo1", gamesequence);
                break;
            case R.id.bu3:
                GAME_BOARD[gamesequence][2] = true;
                editor.putBoolean("s2", true);
                editor.putInt("xo2", gamesequence);
                break;
            case R.id.bu4:
                GAME_BOARD[gamesequence][3] = true;
                editor.putBoolean("s3", true);
                editor.putInt("xo3", gamesequence);
                break;
            case R.id.bu5:
                GAME_BOARD[gamesequence][4] = true;
                editor.putBoolean("s4", true);
                editor.putInt("xo4", gamesequence);
                break;
            case R.id.bu6:
                GAME_BOARD[gamesequence][5] = true;
                editor.putBoolean("s5", true);
                editor.putInt("xo5", gamesequence);
                break;
            case R.id.bu7:
                GAME_BOARD[gamesequence][6] = true;
                editor.putBoolean("s6", true);
                editor.putInt("xo6", gamesequence);
                break;
            case R.id.bu8:
                GAME_BOARD[gamesequence][7] = true;
                editor.putBoolean("s7", true);
                editor.putInt("xo7", gamesequence);
                break;
            case R.id.bu9:
                GAME_BOARD[gamesequence][8] = true;
                editor.putBoolean("s8", true);
                editor.putInt("xo8", gamesequence);
                break;
        }
        editor.apply();
    }

    int referee(int GameCounter){
        int winner;
        int x = 0;
        int s = 0;
        int secdS[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 0, 4, 8, 0, 3, 6, 1, 4, 7, 2, 5, 8, 6, 4, 2};
        do {
            PLAYER2_ST = GAME_BOARD[0][secdS[s]] & GAME_BOARD[0][secdS[s + 1]] & GAME_BOARD[0][secdS[s + 2]];
            PLAYER1_ST = GAME_BOARD[1][secdS[s]] & GAME_BOARD[1][secdS[s + 1]] & GAME_BOARD[1][secdS[s + 2]];
            if (PLAYER1_ST) {
                GameCounter = 10;
                winner = 1;
                break;
            } else if (PLAYER2_ST) {
                GameCounter = 10;
                winner = 2;
                break;
            } else winner = 3;
            s += 3;
            x++;
        } while (x < 8);
        return winner;
    }
}
