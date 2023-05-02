package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Match;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.Hand;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;
import com.sun.tools.javac.jvm.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * This class represents an Assembly Station where the player can use their hand to create an item.
 * It extends the Station class and implements the Interactable interface.
 */
public class AssemblyStation extends Station{
    private Item[] items;
    private Integer Counter;
    private Hand hand;
    private Bubble bubble;
    private Match match;
    private ArrayList<Vector2> handPositions;
    /**
     * Constructor for an AssemblyStation.
     * @param objectPosition The position of the object in the game world.
     * @param width The width of the object.
     * @param height The height of the object.
     * @param handPositions The positions where the hand can be when interacting with the object.
     * @param bubbleRenderer The renderer used to display the bubble.
     * @param match The current game match.
     */
    public AssemblyStation(Vector2 objectPosition, float width, float height, ArrayList<Vector2> handPositions, BubbleRenderer bubbleRenderer, Match match){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Assembly_Station");
        this.bubble = new ActionBubble(bubbleRenderer, new Vector2(handPositions.get(2).x - 8 * Constants.UNIT_SCALE, handPositions.get(2).y), Animations.gearAnimation);
        this.handPositions = handPositions;
        this.hand = new Hand(); // For this station, hand is the same hand as the player uses as it also holds 4 things.
        hand.currentHandSize = hand.baseHandSize + 1;
        stationUseTime = 10f;
        this.match = match;
    }
    {
        this.items = new Item[4];
        this.items[0] = null;
        this.items[1] = null;
        this.items[2] = null;
        this.items[3] = null;
        this.Counter = 0;
    }
    // empty constructor used for tests
    public AssemblyStation()    {
        this.hand = null;
        stationUseTime = 10f;
    }
    /**
     * Setter method for the list of items held in the hand.
     * @param items The list of items to be set.
     */
    public void setItems(ArrayList<Item> items){
        hand.heldItems = items;
    }
    /**
     * Method to pick up an item from the Assembly Station.
     * @param player The player who is trying to pick up the item.
     * @return True if the item was picked up, false otherwise.
     */
    @Override
    public boolean pickUp(Player player) {
        if (hand.isEmpty() || player.hand.isFull()|| inuse){
            return false;
        } else {
            player.hand.give(this.hand.drop());
            pick_up.play();
            return true;
        }
    }
    /**
     * Method to drop off an item at the Assembly Station.
     * @param player The player who is trying to drop off the item.
     * @return True if the item was dropped off, false otherwise.
     */
    @Override
    public boolean dropOff(Player player){
        if (hand.isFull() || player.hand.isEmpty() || inuse){
            return false;
        } else {
            this.hand.give(player.hand.drop());
            put_down.play();
            return true;
        }
    }
    @Override
    public void dumpHand(){
        this.hand.drop();
        this.hand.drop();
        this.hand.drop();
        this.hand.drop();

    }
    /**
     * Method to store an item in the Assembly Station.
     * @param x The item to be stored.
     */
    public void StoreItem(Item x)
    {
        this.items[this.Counter] = x;
        if(this.Counter == 2) //To get correct spare position
        {
            this.Counter = 0;
        }
        else
        {
            this.Counter = this.Counter + 1;
        }
    }
    /**
     * Method to compare two strings.
     * @param A The first string to be compared.
     * @param B The second string to be compared.
     * @return 1 if the strings are equal and not "found", 0 otherwise.
     */
    private int Compare(String A, String B)
    {
        if(A.compareTo(B)==0)
        {
            if(A != "found")
            {
                return 1;
            }
        }
        return 0;
    }
    /**
     * Method to test if a list of strings contains certain elements.
     * @param Test The list of strings to be tested.
     * @param data The list of strings to be searched.
     * @param n The name of the item to be returned if the test is successful.
     * @return The item with the given name if the test is successful, null otherwise.
     */
    public Item TestingForFood(String[] Test, String[] data, String n)
    {
        String[] copiedData = Arrays.copyOf(data, data.length);
        int Total = 0;
        for(int i = 0; i<Test.length;i++)
        {
            for(int j = 0; j<copiedData.length;j++)
            {
                int adder = Compare(Test[i], copiedData[j]);
                Total = Total + adder;
                if(adder==1)
                {
                    Test[i] = "found";
                    copiedData[j] = "found";
                }
            }


        }
        System.out.println(Total);
        return Total == Test.length ? ItemRegister.itemRegister.get(n) : null;
    }
    /**
     * Method to perform an action at the Assembly Station.
     * @param player The player who is trying to perform the action.
     * @return True if the action was performed, false otherwise.
     */
    @Override
    public boolean action(final Player player){
        if(inuse) return false;
        if (this.hand.getHeldItems().size()>1){
            final Item product = getProduct();
            if(product == null) return false;
            inuse = true;
            player.disableMovement();
            this.bubble.setVisible(true);

            Timer timer = new Timer();
            timer.scheduleTask(new Task() {
                @Override
                public void run() {
                    Construct(product);
                    bubble.setVisible(false);
                    player.enableMovement();
                    inuse = false;
                }
            }, getAdjustedStationUseTime());
            return true;
        }
        return false;

    }

    /**
     * Method to get the product created by the list of items held in the hand.
     * @return The product created by the list of items, or null if no product can be created.
     */
    private Item getProduct(){
        List<String> ItemStackTemp = new ArrayList<>();
        Item product = null;
        for (Item temp : this.hand.getHeldItems()){
            ItemStackTemp.add(temp.name);
        }
        String[] ItemStack = ItemStackTemp.toArray(new String[0]);

        if((this.hand.getHeldItems().size() > 1)) {
            HashMap<String, String[]> validRecipes = new HashMap<>();
            //Testing data
            validRecipes.put("Salad", new String[]{"Cut_Tomato", "Cut_Lettuce", "Cut_Onion"});
            validRecipes.put("Burger", new String[]{"Cooked_Bun", "Cooked_Bun", "Cooked_Patty"});
            validRecipes.put("Pizza", new String[]{"Base", "Cooked_Tomato", "Cheese", "Meat"});
            validRecipes.put("JacketPotato", new String[]{"Cut_Potato", "Cheese"});

            for (String foodOutput : validRecipes.keySet()) {
                product = TestingForFood(validRecipes.get(foodOutput), ItemStack, foodOutput);
                if (product != null) return product;
            }
        }
        return null;
    }

    /**
     * Method to construct an item from the list of items held in the hand.
     * @param product The item to be constructed.
     */
    public void Construct(Item product)
    {
        if(product != null) //Delete ingredients and leave Product only waiting to go to stack.
        {
            this.dumpHand();
            this.hand.give(product);
        }
    }

    @Override
    public void renderCounter(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        if (!this.hand.isEmpty()) {
            ArrayList<Item> heldItems = this.hand.getHeldItems();

            for (int i = 0; i < heldItems.size(); i++){
                spriteBatch.draw(heldItems.get(i).getTexture(), this.handPositions.get(i).x, this.handPositions.get(i).y, 8 * Constants.UNIT_SCALE, 8 * Constants.UNIT_SCALE);


            }
        }
        spriteBatch.end();

    }

    public Hand getHand(){
        return hand;
    }
}
