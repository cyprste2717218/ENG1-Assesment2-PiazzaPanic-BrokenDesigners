package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class VoidItem extends Item{
    Texture texture = new Texture("items/void.png");

    public VoidItem(){
        super("Void");
    }

    @Override
    public Texture getTexture(){
        return texture;
    }
}
