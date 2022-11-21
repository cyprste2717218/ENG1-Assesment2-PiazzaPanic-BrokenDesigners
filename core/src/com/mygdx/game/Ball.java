package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import static com.sun.org.apache.xalan.internal.lib.ExsltMath.sqrt;

public class Ball {
    int x;
    int y;
    int radius;
    int x_change;
    int y_change;
    ShapeRenderer renderer;

    Balls balls;

    public Ball(int x, int y, int radius, int x_change, int y_change){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.x_change = x_change;
        this.y_change = y_change;

    }
    public void update(){
        if (this.x + this.x_change >= Gdx.graphics.getWidth() -  this.radius || this.x + this.x_change < this.radius){
            this.x_change = this.x_change * -1;
        }
        if (this.y + this.y_change >= Gdx.graphics.getHeight() - this.radius || this.y + this.y_change < this.radius){
            this.y_change = this.y_change * -1;

        }
        /* for(Ball ball : balls.ballList){
            if(!(ball.getX() == this.getX() && ball.getY() == this.getY())){
                if(this.hasCollided(ball)){
                    int grad_x = this.getX() - ball.getX();
                    int grad_y = this.getY() - ball.getY();
                    this.x_change = grad_x;
                    this.y_change = grad_y;
                }
            }
        }*/
        this.x += this.x_change;
        this.y += this.y_change;
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.circle(x, y, radius);
        renderer.end();
    }
    public void setRenderer(ShapeRenderer renderer){
        this.renderer = renderer;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

}
