package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Match;

public class BakingStation extends Station {

    Match match;

    public BakingStation(Vector2 objectPosition, float width, float height, float handX, float handY, Match match){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Baking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.match = match;
    }
    //timer.scheduleTask(task, 4f * match.getDifficultyLevel().getSpeedMultiplier());

}
