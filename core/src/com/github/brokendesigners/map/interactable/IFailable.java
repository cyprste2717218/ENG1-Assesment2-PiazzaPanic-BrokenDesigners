package com.github.brokendesigners.map.interactable;

import com.github.brokendesigners.Player;

/**
 * The interface Failable - implemented by stations where the user can fail preparation steps.
 */
public interface IFailable {


    /**
     * Handles user interaction for station preparation steps within the MainGame class.
     */
    public void handleStationInteraction();

    /**
     * The function called when the player successfully completes a preparation step
     *
     * @param player            the player
     * @param endingStationTime the time left before the station completes its task
     * @return the boolean true
     */
    public boolean finishSuccessfulOperation(final Player player, float endingStationTime); //The function that is called when the user successfully completes their cooking task

    /**
     * The function called when the player fails a preparation step
     *
     * @param player            the player
     * @param endingStationTime the the time left before the station completes its task
     * @return the boolean false
     */
    public boolean finishFailedOperation(final Player player, float endingStationTime);

    /**
     * A helper function that collates shared code between successful and failed operations
     *
     * @param player the player
     */
    public void generalFinish(Player player);


}
