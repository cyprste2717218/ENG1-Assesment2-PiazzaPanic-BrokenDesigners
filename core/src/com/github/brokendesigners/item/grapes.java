package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class grapes extends Item{

    public Texture texture = new Texture("items/grapes.png");

    public grapes(){
        super("grapes");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
