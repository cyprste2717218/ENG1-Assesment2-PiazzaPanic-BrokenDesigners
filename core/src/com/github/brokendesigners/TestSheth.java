package com.github.brokendesigners;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestSheth {

    int x;
    int y;
    int x_change;
    int y_change;
    float width;
    float height;
    Texture texture;
    SpriteBatch spriteBatch;

    public TestSheth(int x, int y, int x_change, int y_change, float width, float height, Texture texture, SpriteBatch spriteBatch){
        this.x = x;
        this.y = y;
        this.x_change = x_change;
        this.y_change = y_change;
        this.width = width;
        this.height = height;
        this.spriteBatch = spriteBatch;
        this.texture = texture;

    }
    public void setRenderer(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
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

        spriteBatch.draw(this.texture, this.x, this.y, this.width, this.height);
        spriteBatch.end();
    }
}
