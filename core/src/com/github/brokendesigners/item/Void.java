package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Void extends Item{
    Texture texture = new Texture("items/void.png");

    public Void(){}

    @Override
    public Texture getTexture(){
        return texture;
    }
}
