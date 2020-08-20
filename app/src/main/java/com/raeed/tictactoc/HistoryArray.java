package com.raeed.tictactoc;

public class HistoryArray {

    public HistoryArray(String player_A, String player_B, int winer_Id) {
        Player_A = player_A;
        Player_B = player_B;
        Winer_Id = winer_Id;
    }

    private String Player_A;
    private String Player_B;
    private int Winer_Id;

    public String getPlayer_A() {
        return Player_A;
    }

    public void setPlayer_A(String player_A) {
        Player_A = player_A;
    }

    public String getPlayer_B() {
        return Player_B;
    }

    public void setPlayer_B(String player_B) {
        Player_B = player_B;
    }

    public int getWiner_Id() {
        return Winer_Id;
    }

    public void setWiner_Id(int winer_Id) {
        Winer_Id = winer_Id;
    }
}
