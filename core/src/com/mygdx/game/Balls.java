package com.mygdx.game;

import java.util.ArrayList;

public class Balls {
    ArrayList<Ball> ballList = new ArrayList<Ball>();

    public void addBall(Ball ball){
        ballList.add(ball);
    }
    public void removeBall(Ball ball){
        ballList.remove(ball);
    }
}
