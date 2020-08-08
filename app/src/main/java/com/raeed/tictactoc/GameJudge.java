package com.raeed.tictactoc;

public class GameJudge {

    public boolean iswin(boolean isA,boolean isB,boolean isC,boolean isD,boolean isE,boolean isF,boolean isG,boolean isH,boolean isI){
        boolean isW=false;
        if (isA & isB & isC) {
            isW = true;
        } else if (isD & isE & isF) {
            isW = true;
        } else if (isC & isF & isI) {
            isW = true;
        } else if (isG & isH & isI) {
            isW = true;
        }else if (isA & isE & isI) {
            isW = true;
        } else if (isA & isD & isG) {
        isW = true;
        } else if (isB & isE & isH) {
        isW = true;
        } else if (isC & isE & isG) {
        isW = true;
        }
        return isW;
    }
}
