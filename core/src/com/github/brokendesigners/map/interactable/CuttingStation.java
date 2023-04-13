package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.item.ItemRegister;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

public class CuttingStation extends Station implements IFailable{

    static final String[] Cuttables = {"Tomato", "Lettuce", "Onion", "Potato"};
    Bubble cuttingBubble, countdownBubble;

    public boolean needsInteraction, cutToEarly, isValidCuttingTime;

    public CuttingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, boolean locked){
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
        stationUseTime = 4f;
    }
    public CuttingStation() {}

    //Readies the station for use when the player first interacts with it
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


    //If the player interacted with the station at the right moment, the cutting succeeds, otherwise it fails.
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


    //After some cutting, the station begins its 3 second countdown, using isValidCuttingTime to flag that the player can cut at any point during the countdown
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
        timer.scheduleTask(task, stationUseTime);
    }

    //Cutting Operation
    @Override
    public boolean action(final Player player) {
        // to unlock the station
        if (this.locked)    {
            this.unlockStation();
            unlockFX.play();
            System.out.println("Station Unlocked");
            return false;
        }
        if(inuse || hand == null) return false;
        if (Applicable(Cuttables, "Cutting_Station", hand.getName())) {
            setUpCutting(player);
            final Timer timer = new Timer();
            handleCutttingCountdown(timer, player);
            return true;
        } else {
            System.out.println("Incorrect Item");
            failure.play();
        }
        return false;
    }
    // Both functions used for testing
    public String[] getCuttables()  {
        return Cuttables;
    }
    public void setName(String name)   {
        this.station_name = name;
    }

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
