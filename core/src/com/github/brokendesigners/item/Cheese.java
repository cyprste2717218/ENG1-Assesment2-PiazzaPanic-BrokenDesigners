package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cheese extends Item{

    public Texture texture = new Texture("items/cheese.png");

    public Cheese() {
        super("Cheese");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

}
