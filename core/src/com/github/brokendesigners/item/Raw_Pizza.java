package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Raw_Pizza extends Item{

    public Texture texture = new Texture("items/pizza_raw.png");

    public Raw_Pizza() {
        super("Raw_Pizza");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
