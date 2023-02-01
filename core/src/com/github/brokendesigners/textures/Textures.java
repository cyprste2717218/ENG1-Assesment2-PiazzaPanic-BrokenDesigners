package com.github.brokendesigners.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/*
 * Contains static textures for customers and bubbles
 */
public class Textures {
    public static Texture bluggus_customer_texture = new Texture("characters/bluggus.png");
    // ^^ bluggus is the green alien that arrives as a customer

    public static Texture simple_bubble = new Texture("bubbles/simple_bubble.png");
    // ^^ Simple bubble is the white cloud that appears when you interact with a station
    public static Texture simple_item_bubble = new Texture("bubbles/simple_bubble_with_item.png");
    // ^^ Appears above customer -- identical to simple bubble except has a space for an item.

    public static void dispose() {
        bluggus_customer_texture.dispose();
        simple_bubble.dispose();
        simple_item_bubble.dispose();
    }
}
