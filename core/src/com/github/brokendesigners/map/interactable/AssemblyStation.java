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

public class AssemblyStation extends Station{
    private Item[] items;
    private Integer Counter;
    private Hand hand;
    private Bubble bubble;
    private Match match;
    private ArrayList<Vector2> handPositions;

    /*
     * Instantiates AssemblyStation
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

    //Override storing products

    //return Product or spare ingredients

    public void setItems(ArrayList<Item> items){
        hand.heldItems = items;
    }

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
/*
    @Override
    public boolean pickUp(Player player){
        if (this.hasEmptyHand() || player.hand.isFull()){
            return false;
        } else {
            player.hand.give(pickup()); //Special form of pickup
            this.dumpHand();
            return true;
        }
    } */

    //set item position
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

    //Gets the product created by the given list of items
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

    //Construct Product
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
