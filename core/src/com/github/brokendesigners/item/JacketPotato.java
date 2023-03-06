package com.github.brokendesigners.item;

import com.badlogic.gdx.graphics.Texture;

public class JacketPotato extends Item{

    public Texture texture = new Texture("items/jacketpotato.png");

    public JacketPotato() {
        super("JacketPotato");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
