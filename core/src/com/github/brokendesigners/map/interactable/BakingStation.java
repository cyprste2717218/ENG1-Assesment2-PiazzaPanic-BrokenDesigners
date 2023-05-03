package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;

/**
 *
 *Extended Class
 */
public class BakingStation extends Station implements IFailable {

    static final String[] Bakeables = {"Pizza","JacketPotato"};
    Bubble bakingBubble, attentionBubble;
    boolean canBurn;
    Match match;
    /**
     * Constructor for BakingStation class
     *
     * @param objectPosition The position of the baking station
     * @param width The width of the baking station
     * @param height The height of the baking station
     * @param handX The x-position of the hand
     * @param handY The y-position of the hand
     * @param bubbleRenderer The bubble renderer to use
     * @param locked Whether the station is locked or not
     * @param match The current match
     */
    public BakingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, boolean locked, Match match){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Baking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.bakingBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.gearAnimation);
        this.attentionBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.attentionAnimation);
        this.locked = locked;
        stationUseTime = 4f;
        canBurn = false;
        this.match = match;
    }
    /**
     * Created Constructor
     * Constructor for BakingStation class that takes a fake bubble
     *
     * @param fakeBubble The fake bubble to use
     */
    public BakingStation(Bubble fakeBubble)    {
        this.station_name = "Baking_Station";
        this.hand = null;
        canBurn = true;
        this.attentionBubble = fakeBubble;


    }
    /**
     * Created Task
     *
     * Burns the food if required
     */
    public Timer.Task burnFood = new Timer.Task() {

        @Override
        public void run() {
            if(canBurn){
                hand = ItemRegister.itemRegister.get("Waste");
                attentionBubble.setVisible(false);
                canBurn = false;
            }
        }
    };

    public BakingStation() {
        stationUseTime = 4f;
    }

    /**
     * Createde method
     *
     * Performs the action of using the baking station
     *
     * @param player The player performing the action
     * @return true if the action was successful, false otherwise
     */
    @Override
    public boolean action(final Player player) {
        // to unlock the station
        unlockStation();

        // if player is holding something, station is not already in use and item
        // in hand has not already been cooked
        if(inuse || hand == null || hand.Baking) return false;
        System.out.println(hand.getName());
        //If an item is bakeable, successfully bake the item, otherwise prevent the player from using the station
        if (Applicable(Bakeables, "Baking_Station", hand.name)) {
            System.out.println("gotHERE");
            this.inuse = true;
            this.bakingBubble.setVisible(true);
            finishSuccessfulOperation(player, getAdjustedStationUseTime());
            return true;
        } else {
            return finishFailedOperation(player, stationUseTime);
        }
    }

    /**
     * Created method
     *
     * Handles the burning of items and its prevention
     */
    @Override
    public void handleStationInteraction() {
        if(hand == null){
            canBurn = false;
            attentionBubble.setVisible(false);
        }
        if(!canBurn) return;
        final Timer timer = new Timer();
        if(!burnFood.isScheduled()){
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    attentionBubble.setVisible(true);
                }
            }, 2f);
            timer.scheduleTask(burnFood, 7f);
        }
    }

    /**
    * Created method
     */
    @Override
    public boolean finishSuccessfulOperation(Player player, float endingStationTime) {
        Timer timer = new Timer();
        //Gives the player the baked item after the correct amount of time
        Timer.Task finishBaking = new Timer.Task() {
            @Override
            public void run() {
                hand = ItemRegister.itemRegister.get("Baked_" + hand.getName());
                hand.Baking = true;
                System.out.println("Hand: "+ hand.getName());
                bakingBubble.setVisible(false);
                inuse = false;
                //Starts the burning timer
                canBurn = true;
            }
        };
        timer.scheduleTask(finishBaking, endingStationTime);
        return true;
    }


    /**
     * Created method
     */
    @Override
    public boolean finishFailedOperation(Player player, float endingStationTime) {
        System.out.println("Incorrect Item");
        failure.play();
        return false;
    }

    /**
     * Created method
     */
    @Override
    public void generalFinish(Player player) {
        //Left empty for this class
    }
    public String[] getBakeables()  {
        return Bakeables;
    }
}

