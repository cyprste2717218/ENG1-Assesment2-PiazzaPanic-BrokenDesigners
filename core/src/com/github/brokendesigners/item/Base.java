package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Base extends Item{

    public Texture texture = new Texture("items/base.png");
    public Base() {
        super("Base");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
