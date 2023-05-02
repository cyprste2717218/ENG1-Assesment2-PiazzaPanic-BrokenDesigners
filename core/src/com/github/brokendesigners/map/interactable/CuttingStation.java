package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.item.ItemRegister;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;
/**
 * CuttingStation is a class that represents a cutting station in a game.
 * It extends Station and implements IFailable.
 */

public class CuttingStation extends Station implements IFailable{

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion", "Potato"};
    Bubble cuttingBubble, countdownBubble;
    Match match;
    public boolean needsInteraction, cutToEarly, isValidCuttingTime;
    /**
     * Constructs a CuttingStation object with the specified parameters
     *
     * @param objectPosition - the position of the object
     * @param width - the width of the object
     * @param height - the height of the object
     * @param handX - the x position of the hand
     * @param handY - the y position of the hand
     * @param bubbleRenderer - the bubble renderer
     * @param locked - whether the station is locked
     * @param match - a Match object that tracks the state of the game
     */
    public CuttingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, boolean locked, Match match){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cutting_Station");
        this.handPosition = new Vector2(handX, handY);
        this.cuttingBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
            Animations.cuttingAnimation);
        countdownBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.countdownAnimation);
        stationUseTime = 2f;
        needsInteraction = false;
        cutToEarly = false;
        isValidCuttingTime = false;
        this.locked = locked;
        this.match = match;
    }

    public CuttingStation() {
        this.station_name = "Cutting_Station";
        this.hand = null;
        stationUseTime = 2f;
    }
    /**
     * Readies the station for use when the player first interacts with it.
     *
     * @param player - the player who interacts with the station
     */
    private void setUpCutting(Player player){
        //Resets the countdown animation
        countdownBubble.resetStateTime();
        System.out.println("Worked");
        inuse = true;
        //Disables the players movement
        player.disableMovement();
        player.hand.disable_hand_ability();
        cuttingBubble.setVisible(true);
    }
    /**
     * If the player interacted with the station at the right moment, the cutting succeeds, otherwise it fails.
     *
     * @param timer - the Timer object that tracks time
     * @param player - the player who interacts with the station
     */
    private void handleCuttingInteraction(final Timer timer, final Player player){
        needsInteraction = true;
        timer.scheduleTask(new Task() {
            @Override
            public void run() {
                countdownBubble.setVisible(false);
                boolean wasSuccessful = (cutToEarly || needsInteraction) ? finishFailedOperation(player, stationUseTime) : finishSuccessfulOperation(player, stationUseTime);
            }
        }, 1f);
    }
    /**
     * Handles the cutting countdown by displaying a cutting bubble and a countdown timer.
     * @param timer The Timer object used to handle the countdown.
     * @param player The Player object interacting with the Cutting Station.
     */
    private void handleCutttingCountdown(final Timer timer, final Player player){
        Task task = new Task() {
            @Override
            public void run() {
                cuttingBubble.setVisible(false);
                countdownBubble.setVisible(true);
                isValidCuttingTime = true;
                timer.scheduleTask(new Task() {
                    @Override
                    public void run() {
                        //Handles the cutting
                       handleCuttingInteraction(timer, player);
                    }
                }, 3f);
            }
        };
        timer.scheduleTask(task, getAdjustedStationUseTime());
    }
    /**
     * Performs the cutting operation by setting up the cutting interaction and starting the cutting countdown.
     * @param player The Player object interacting with the Cutting Station.
     * @return Returns true if the cutting operation is successful, false otherwise.
     */
    @Override
    public boolean action(final Player player) {
        // to unlock the station
        unlockStation();
        if(inuse || hand == null) return false;
        if (Applicable(Cuttables, "Cutting_Station", hand.getName())) {
            setUpCutting(player);
            final Timer timer = new Timer();
            handleCutttingCountdown(timer, player);
            return true;
        } else {
            System.out.println("Incorrect Item");
            //failure.play();
        }
        return false;
    }
    /**
     * Gets the array of cuttable items.
     * @return Returns the array of cuttable items.
     */
    public String[] getCuttables()  {
        return Cuttables;
    }
    public void setName(String name)   {
        this.station_name = name;
    }
    /**
     * Handles the interaction with the Cutting Station by checking if the player presses space during the countdown and flagging if the cutting was successful or not.
     */
    @Override
    public void handleStationInteraction() {
        //If the player presses space during the countdown
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isValidCuttingTime){
            //If it is the right time to cut
            if(needsInteraction){
                needsInteraction = false;
            }
            else{ //Otherwise they cut too early
                cutToEarly = true;
            }
        }
    }

    //Gives the player the correctly cut item
    @Override
    public boolean finishSuccessfulOperation(Player player, float endingStationTime) {
        hand = ItemRegister.itemRegister.get("Cut_" + hand.getName());
        generalFinish(player);
        return true;
    }

    //Gives the player a waste item
    @Override
    public boolean finishFailedOperation(Player player, float endingStationTime) {
        System.out.println("Cut to Early: "+ cutToEarly);
        System.out.println("Meeds Interaction: " + needsInteraction);
        hand = ItemRegister.itemRegister.get("Waste");
        generalFinish(player);
        return false;
    }
    /**
     This method is called when the cutting operation is completed, regardless of whether it was successful or not.
     It restores the player's ability to move and use their hand, and resets all cutting-related variables.
     @param player the player object
     */
    @Override
    public void generalFinish(Player player) {
        player.enableMovement();
        player.hand.enable_hand_ability();
        inuse = false;
        needsInteraction = false;
        cutToEarly = false;
        isValidCuttingTime = false;
    }
}
