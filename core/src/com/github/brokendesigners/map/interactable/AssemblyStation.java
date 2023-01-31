package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.brokendesigners.Constants;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.bubble.ActionBubble;
import com.github.brokendesigners.bubble.Bubble;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.Hand;

import com.github.brokendesigners.renderer.BubbleRenderer;
import com.github.brokendesigners.textures.Animations;
import java.util.ArrayList;

public class AssemblyStation extends Station{
    private Item[] items;
    private Item Product;
    private Integer Counter;
    private Hand hand;
    private Bubble bubble;

    private ArrayList<Vector2> handPositions;

    public AssemblyStation(Vector2 objectPosition, float width, float height, ArrayList<Vector2> handPositions, BubbleRenderer bubbleRenderer){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Assembly_Station");
        this.bubble = new ActionBubble(bubbleRenderer, new Vector2(handPositions.get(2).x - 8 * Constants.UNIT_SCALE, handPositions.get(2).y), Animations.gearAnimation);
        this.handPositions = handPositions;
        this.hand = new Hand();
    }
    {
        this.items = new Item[3];
        this.items[0] = null;
        this.items[1] = null;
        this.items[2] = null;
        this.Product = null;
        this.Counter = 0;
    }
    //Override storing products

    //return Product or spare ingredients

    @Override
    public boolean pickUp(Player player) {
        if (hand.isEmpty() || player.hand.isFull()||this.inuse){
            return false;
        } else {
            player.hand.give(this.hand.drop());
            pick_up.play();
            return true;
        }
    }
    @Override
    public boolean dropOff(Player player){
        if (hand.isFull() || player.hand.isEmpty()){
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

    private Item TestingForFood(String[] Test, String[] data, String n)
    {
        int Total = 0;
        for(int i = 0; i<Test.length;i++)
        {
            for(int j = 0; j<data.length;j++)
            {
                int adder = Compare(Test[i], data[j]);
                System.out.println(Compare(Test[i], data[j]));
                Total = Total + adder;
                if(adder==1)
                {
                    Test[i] = "found";
                    data[j] = "found";
                }
            }


        }
        System.out.println(Total);
        if(Total == 3)
        {
            return ItemRegister.itemRegister.get(n);
        }
        else
        {
            return null;
        }

        
        
    }
    @Override
    public boolean action(final Player player){
        if (this.inuse == true) {
            return false;
        } else{

            if (this.hand.isFull()){
                this.inuse = true;
                player.disableMovement();
                this.bubble.setVisible(true);

                Timer timer = new Timer();
                timer.scheduleTask(new Task() {
                    @Override
                    public void run() {
                        Construct();
                        bubble.setVisible(false);
                        player.enableMovement();
                        inuse = false;
                    }
                }, 10f);
                return true;
            }
        }
        return false;

    }

    //Construct Product
    public void Construct()
    {
        String[] ItemStack = new String[]{this.hand.getHeldItems().get(0).name, this.hand.getHeldItems().get(1).name, this.hand.getHeldItems().get(2).name};
        if((this.hand.isFull()))
        {
            //Testing data
            String[] SaladTest = new String[]{"Cut_Tomato","Cut_Lettuce","Cut_Onion"};
            String[] BurgerTest = new String[]{"Cooked_Bun","Cooked_Bun","Cooked_Patty"};
            // String[] "Product"Test = new String[]{"Ingredient1","Ingredient2","Ingredient3"};
            // String[] *****Test = new String[]{"","",""};
            // String[] *****Test = new String[]{"","",""};

            if(this.Product == null) //Test for burger
            {
                //Test for burger
                this.Product = TestingForFood(BurgerTest,ItemStack,"Burger");
            }
            if(this.Product == null) //Test for Salad
            {
                this.Product = TestingForFood(SaladTest,ItemStack,"Salad");
            }
            // if(this.Product == null) //Test for
            // {
            //     this.Product = TestingForFood(*****Test,ItemStack,*******);
            // }
            // if(this.Product == null) //Test for
            // {
            //     this.Product = TestingForFood(*****Test,ItemStack,*******);
            // }

            System.out.println(this.Product);
            if(this.Product != null) //Delete ingredients and leave Product only waiting to go to stack.
            {

                this.dumpHand();
                this.hand.give(this.Product);
                this.Product = null;

            }
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
}
