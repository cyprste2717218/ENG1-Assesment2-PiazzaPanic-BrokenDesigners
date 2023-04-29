package com.github.brokendesigners.menu.Buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.menu.MenuScreen;

public abstract class Button {

    Rectangle rectangle;
    Texture selectedTexture, unselectedTexture;
    boolean rendered, selected;
    MenuScreen menuScreen;

    public Button(Rectangle rectangle, Texture selectedTexture, Texture unselectedTexture, MenuScreen menuScreen){
        this.rectangle = rectangle;
        this.selectedTexture = selectedTexture;
        this.unselectedTexture = unselectedTexture;
        this.menuScreen = menuScreen;
        rendered = true;
        menuScreen.getMenuButtons().add(this);
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    //Checks to see if the current button is being hovered
    public boolean isHovered(OrthographicCamera camera){
        Vector3 touchedPoint = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return rectangle.contains(new Vector2(touchedPoint.x, touchedPoint.y)) && isRendered();
    }

    /***
     * Checks if a button meets all the criteria to be activated
     * @param camera
     * @return
     */
    public boolean onActivate(OrthographicCamera camera){
        if(isHovered(camera) && Gdx.input.justTouched()) return performTask();
        if(isSelected() && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) return performTask();
        return false;
    }

    public abstract boolean performTask();

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /***
     *
     * A function that controls if a button should be rendered and whether it will use its selected texture or not
     *
     * @param batch
     * @param camera
     */
    public void render(SpriteBatch batch, OrthographicCamera camera){
        if(!isRendered()) return;
        Texture drawnTexture = isSelected() || isHovered(camera) ? selectedTexture : unselectedTexture;
        batch.draw(drawnTexture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public MenuScreen getMenuScreen(){
        return menuScreen;
    }
}
