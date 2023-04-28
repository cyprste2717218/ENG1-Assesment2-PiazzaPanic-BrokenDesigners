package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Potato extends Item{

    public Texture texture = new Texture("items/potato.png");

    public Potato() {
        super("Potato");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture){
        this.texture = texture;

    }
}
