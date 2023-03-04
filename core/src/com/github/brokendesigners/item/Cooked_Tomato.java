package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cooked_Tomato extends Item{

    public Texture texture = new Texture("items/tomato_cooked.png");

    public Cooked_Tomato() {
        super("Cooked_Tomato");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
