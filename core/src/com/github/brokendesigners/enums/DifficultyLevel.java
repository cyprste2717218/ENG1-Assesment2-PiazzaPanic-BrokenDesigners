package com.github.brokendesigners.enums;

/***
 * Created Enum
 */
public enum DifficultyLevel {
    EASY(0.5f),
    MEDIUM(1f),
    HARD(2f);

    private final float speedMultiplier;

    DifficultyLevel(float speedMultiplier){
        this.speedMultiplier = speedMultiplier;
    }
    public float getSpeedMultiplier(){
        return speedMultiplier;
    }

}
