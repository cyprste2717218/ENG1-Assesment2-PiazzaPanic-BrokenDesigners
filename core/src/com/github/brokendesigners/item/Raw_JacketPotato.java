package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Raw_JacketPotato extends Item{

    public Texture texture = new Texture("items/jacketpotato_raw.png");

    public Raw_JacketPotato() {
        super("Raw_JacketPotato");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
