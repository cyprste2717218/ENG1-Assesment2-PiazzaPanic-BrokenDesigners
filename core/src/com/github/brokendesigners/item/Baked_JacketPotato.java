package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class Baked_JacketPotato extends Item {

    public Texture texture = new Texture("items/jacketpotato_baked.png");

    public Baked_JacketPotato() {
        super("Baked_JacketPotato",10);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
