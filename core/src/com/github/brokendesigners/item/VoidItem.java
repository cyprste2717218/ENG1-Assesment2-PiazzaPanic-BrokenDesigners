package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class VoidItem extends Item{
    public VoidItem(){
        super("Void");
        texture = new Texture("items/void.png");
    }

    @Override
    public Texture getTexture(){
        return texture;
    }
}
