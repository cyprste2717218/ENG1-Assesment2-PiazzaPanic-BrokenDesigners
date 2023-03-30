package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Cut_Potato extends Item{

    public Texture texture = new Texture("items/potato_cut.png");

    public Cut_Potato() {
        super("Cut_Potato");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
