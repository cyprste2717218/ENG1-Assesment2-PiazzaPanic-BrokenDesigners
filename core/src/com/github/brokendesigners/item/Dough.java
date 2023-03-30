package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Dough extends Item{

    public Texture texture = new Texture("items/dough.png");

    public Dough() {
        super("Dough");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
