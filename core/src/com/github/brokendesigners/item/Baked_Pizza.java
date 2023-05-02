package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Baked_Pizza extends Item{

    public Texture texture = new Texture("items/pizza_baked.png");

    public Baked_Pizza() {
        super("Baked_Pizza");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
