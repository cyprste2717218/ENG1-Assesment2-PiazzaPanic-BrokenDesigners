package com.github.brokendesigners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.*;

public class TestBall {

    int x;
    int y;
    int x_change;
    int y_change;
    int radius;
    ShapeRenderer shapeRenderer;

    public TestBall(int x, int y, int x_change, int y_change, int radius, ShapeRenderer shapeRenderer){
        this.x = x;
        this.y = y;
        this.x_change = x_change;
        this.y_change = y_change;
        this.radius = radius;
        this.shapeRenderer = shapeRenderer;

    }
    public void setRenderer(ShapeRenderer shapeRenderer){
        this.shapeRenderer = shapeRenderer;
    }
    public void update(Viewport viewport){

        float height = viewport.getWorldHeight();
        float width = viewport.getWorldWidth();
        if (this.x + this.x_change > width || this.x + this.x_change < 0){
            this.x_change = this.x_change * -1;

        }
        if (this.y + this.y_change > height || this.y + this.y_change < 0){
            this.y_change = this.y_change * -1;

        }
        this.x += this.x_change;
        this.y += this.y_change;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(this.x, this.y, this.radius);
        shapeRenderer.end();
    }
}
