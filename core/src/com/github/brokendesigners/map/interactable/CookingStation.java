package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.github.brokendesigners.*;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;
import com.badlogic.gdx.utils.Timer.Task;
/**
 * Extended Class
 *
 * A cooking station that can burn food and requires user input for flipping certain items.
 */
public class CookingStation extends Station implements IFailable {

    static final String[] Cookables = {"Patty","Bun", "Tomato"};
    static final String[] Flippables = {"Patty"};

    public boolean canBurn;

    public boolean needsInput;
    private Match match;
    public Bubble cookingBubble, attentionBubble, flippingBubble;

    public CookingStation(Vector2 objectPosition, float width, float height, float handX, float handY, BubbleRenderer bubbleRenderer, boolean locked, Match match){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Cooking_Station");
        this.handPosition = new Vector2(handX, handY);
        this.cookingBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.gearAnimation);
        this.attentionBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.attentionAnimation);
        this.flippingBubble = new ActionBubble(bubbleRenderer, new Vector2(handPosition.x - 8f * Constants.UNIT_SCALE, handPosition.y),
                Animations.flippingAnimation);
        this.locked = locked;
        stationUseTime = 4f;
        canBurn = false;
        needsInput = false;
        this.match = match;
        flippingBubble.setVisible(false);
    }

    /**
     * Created Constructor
     *
     * Used for testing
     *
     * @param fakeBubble
     */
    public CookingStation(Bubble fakeBubble){
        this.station_name = "Cooking_Station";
        this.hand = null;
        canBurn = true;
        this.attentionBubble = fakeBubble;
        this.flippingBubble = fakeBubble;
    }

    /**
     * Created Task
     *
     * A task which burns the food when run, and resets the features of the station to do with burning.
     */
    public Task burnFood = new Task() {
        @Override
        public void run() {
            if(canBurn){
                hand = ItemRegister.itemRegister.get("Waste");
                attentionBubble.setVisible(false);
                canBurn = false;
            }
        }
    };

    public Task showBurning = new Task() {
        @Override
        public void run() {
            attentionBubble.setVisible(true);
        }
    };

    //Test Constructor
    public CookingStation() {
        stationUseTime = 4f;
    }

    /**
     * Created method
     *
     * Starts the timer for the food to burn after being left on the station too long.
     * If the station is empty, the burning should be cancelled.
     */
    public void startFoodBurning(){
        //If the station is empty, the burning should be cancelled
        if(hand == null){
            canBurn = false;
            attentionBubble.setVisible(false);
            flippingBubble.setVisible(false);
            burnFood.cancel();
            showBurning.cancel();
        }
        if(!canBurn) return;
        final Timer timer = new Timer();
        if(!burnFood.isScheduled()){
            //Make the attention bubble visible 2 after 2 seconds of the food being left on the station
            timer.scheduleTask(showBurning, 2f);
            //5 seconds after the attention bubble is shown, the food is scheduled to burn
            timer.scheduleTask(burnFood, 7f);
        }
    }

    /**
     *
     * Created Method
     *
     * Handles the cooking operation.
     * If the player is holding something, the station is not already in use and the item in hand
     * has not already been cooked, and the food placed on the station is cookable,
     * the cooking process succeeds. Otherwise, it fails.
     *
     * @param player The player performing the cooking operation.
     * @return true if the cooking operation is successful, false otherwise.
     */
    @Override
    public boolean action(final Player player) {
        // to unlock the station
        unlockStation();
        // if player is holding something, station is not already in use and item
        // in hand has not already been cooked
        if(inuse || hand == null || hand.Cooking) return false;
        //If the food placed on the station is cookable, lock the player's movement and the station's accessibility
        if (Applicable(Cookables, "Cooking_Station", hand.getName())) {
            inuse = true;
            player.disableMovement();
            player.hand.disable_hand_ability();
            cookingBubble.setVisible(true);
            attentionBubble.setVisible(false);
            //If the food item does not require flipping, the cooking process succeeds, otherwise the flipping is done inside the requiresFlipping() function
            if (!requiresFlipping(player)){
                finishSuccessfulOperation(player, getAdjustedStationUseTime());
            }
        } else {
            System.out.println("Incorrect Item");
            failure.play();
        }
        return false;
    }
    /**
     * Created method
     *
     * A function that handles all food at the CookingStation that needs to be flipped, e.g. patties.
     * If the food is not flippable, it returns false.
     * Otherwise, it waits half of stationUseTime and then has the player flip the food.
     * If the player provides an input of the space bar key within 3 seconds, the operation succeeds, otherwise it fails.
     *
     * @param player The player flipping the food.
     * @return true if the flipping operation is successful, false otherwise.
     */
    private boolean requiresFlipping(final Player player){
        //If the food is not flippable, return false
        if(!Applicable(Flippables, "Cooking_Station", hand.getName())) return false;
        Timer timer = new Timer();
        //Wait half of stationUseTime and then have them flip
        timer.scheduleTask(new Task() {
            @Override
            public void run() {
                needsInput = true;
                cookingBubble.setVisible(false);
                attentionBubble.setVisible(false);
                flippingBubble.setVisible(true);
            }
        }, getAdjustedStationUseTime()/2f);

        //If the player provides an input of the space bar key within 3 seconds, the operation succeeds, otherwise it fails.
        timer.scheduleTask(new Task() {
            @Override
            public void run() {
                boolean wasSuccessful = !needsInput ? finishSuccessfulOperation(player, getAdjustedStationUseTime()/2f) : finishFailedOperation(player, stationUseTime/2f);
            }
        }, 3f);
        return true;
    }
    /**
     * Created method
     *
     * Starts the timer for the food to burn if needed. If the station requires input,
     * this checks to see if that requirement is met.
     */
    @Override
    public void handleStationInteraction() {
        //Starts the timer for the food to burn if needed
        startFoodBurning();
        //If the station requires input, this checks to see if that requirement is met
        if(needsInput && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            needsInput = false;
            flippingBubble.setVisible(false);
            cookingBubble.setVisible(true);
        }
    }

    /**
     * Created method
     *
     * Cancels any burning and puts the cooked version of the item in the station's inventory.
     *
     * @param player The player performing the operation.
     * @param endingCookTime The time it takes to finish cooking the item.
     * @return true, always.
     */
    @Override
    public boolean finishSuccessfulOperation(final Player player, float endingCookTime) {
        burnFood.cancel();
        Timer timer = new Timer();
        Task finishCooking = new Task() {
            @Override
            public void run() {
                hand = ItemRegister.itemRegister.get("Cooked_" + hand.getName());
                generalFinish(player);
                canBurn = true;
            }
        };
        timer.scheduleTask(finishCooking, endingCookTime);
        return true;
    }
    /**
     * Created method
     *
     * Puts the burned item in the station's inventory.
     *
     * @param player The player performing the operation.
     * @param endingCookTime The time it takes to finish cooking the item.
     * @return false, always.
     */
    @Override
    public boolean finishFailedOperation(final Player player, float endingCookTime) {
        Timer timer = new Timer();
        Task finishCooking = new Task() {
            @Override
            public void run() {
                hand = ItemRegister.itemRegister.get("Waste");
                generalFinish(player);
                canBurn = false;
            }
        };
        timer.scheduleTask(finishCooking, endingCookTime);
        return false;
    }
    /**
     *
     * Created method
     *
     * Cleans up after cooking an item. Sets the cooking flag on the item to true, hides
     * the cooking bubble, enables player movement and hand ability, and resets various
     * flags and state variables.
     *
     * @param player The player performing the operation.
     */
    @Override
    public void generalFinish(Player player) {
        hand.Cooking = true;
        System.out.println("Hand: " + hand.getName());
        cookingBubble.setVisible(false);
        player.enableMovement();
        player.hand.enable_hand_ability();
        inuse = false;
        needsInput = false;
        attentionBubble.setVisible(false);
        flippingBubble.setVisible(false);
    }
    public String[] getCookables() {
        return Cookables;
    }
}
