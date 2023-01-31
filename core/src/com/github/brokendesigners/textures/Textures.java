package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Textures {
    public static Texture bluggus_customer_texture = new Texture("characters/bluggus.png");

    public static Texture simple_bubble = new Texture("bubbles/simple_bubble.png");

    public static Texture simple_item_bubble = new Texture("bubbles/simple_bubble_with_item.png");


    public static void dispose() {
        bluggus_customer_texture.dispose();
        simple_bubble.dispose();
        simple_item_bubble.dispose();
    }
}
