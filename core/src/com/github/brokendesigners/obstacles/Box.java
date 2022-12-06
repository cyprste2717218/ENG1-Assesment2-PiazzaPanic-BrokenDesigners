package com.github.brokendesigners.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.brokendesigners.PlayerChef;

public class Box {

    public final int x_coord;
    public final int y_coord;
    public final int WIDTH;
    public final int HEIGHT;

    private Texture texture;

    public Box(int x_coord, int y_coord, int size){
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.WIDTH = size;
        this.HEIGHT = size;

    }
    public Box(int x_coord, int y_coord, int WIDTH, int HEIGHT){
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.WIDTH  = WIDTH ;
        this.HEIGHT  = HEIGHT ;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public void renderBox(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(this.texture, this.x_coord, this.y_coord, this.WIDTH, this.HEIGHT);
        spriteBatch.end();

    }

}
