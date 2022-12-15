package com.github.brokendesigners.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Box {

    public final int x_coord;
    public final int y_coord;
    public final int WIDTH;
    public final int HEIGHT;
    public Vector3 vect;

    private Texture texture;

    public Box(int x_coord, int y_coord, int size){
        this.x_coord = x_coord;
        this.y_coord = y_coord;

        this.vect = new Vector3(this.x_coord, this.y_coord, 0);

        this.WIDTH = size;
        this.HEIGHT = size;

    }
    public Box(int x_coord, int y_coord, int WIDTH, int HEIGHT){
        this.x_coord = x_coord;
        this.y_coord = y_coord;

        this.vect = new Vector3(this.x_coord, this.y_coord, 0);

        this.WIDTH  = WIDTH ;
        this.HEIGHT  = HEIGHT ;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public void renderBox(SpriteBatch spriteBatch){
        Vector3 vect2 = this.vect.cpy();
        vect2.prj(spriteBatch.getProjectionMatrix());


        spriteBatch.begin();
        spriteBatch.draw(this.texture, vect2.x, vect2.y, this.WIDTH, this.HEIGHT);
        spriteBatch.end();

    }

}
