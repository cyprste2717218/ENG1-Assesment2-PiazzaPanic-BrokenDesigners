package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Pizza extends Item{

    public Texture texture = new Texture("items/pizza.png");

    public Pizza() {
        super("Pizza");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
