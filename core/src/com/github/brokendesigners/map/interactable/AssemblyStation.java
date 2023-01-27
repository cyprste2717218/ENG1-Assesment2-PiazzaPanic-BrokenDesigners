package com.github.brokendesigners.map.interactable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.github.brokendesigners.Player;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.item.ItemRegister;
import com.github.brokendesigners.Hand;

public class AssemblyStation extends Station{
    private Item[] items;
    private Item Product;
    private Integer Counter;
    private Hand hand;

    public AssemblyStation(Vector3 objectPosition, float width, float height, float handX, float handY){
        super(new Rectangle(objectPosition.x, objectPosition.y, width, height),"Assembly_Station");
        hand = new Hand();
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
        if (hand.isEmpty() || player.hand.isFull()){
            return false;
        } else {
            player.hand.give(this.hand.drop());
            return true;
        }
    }
    @Override
    public boolean dropOff(Player player){
        if (hand.isFull() || player.hand.isEmpty()){
            return false;
        } else {
            this.hand.give(player.hand.drop());
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
                Total = Total + adder;
                if(adder==1)
                {
                    Test[i] = "found";
                    data[j] = "found";
                }
            }


        }
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
    public boolean action(Player player){
        if (this.hand.isFull()){
            System.out.println("ENGAGED");
            this.Construct();
            return true;
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
            //     this.Product = TestingForFood();
            // }
            // if(this.Product == null) //Test for
            // {
            //     this.Product = TestingForFood();
            // }


            if(this.Product != null) //Delete ingredients and leave Product only waiting to go to stack.
            {
                this.dumpHand();
                this.hand.give(Product);
            }
        }
    }

    /*@Override
    public void renderCounter(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Item item : this.hand.getHeldItems()){

        }
    }*/
}
