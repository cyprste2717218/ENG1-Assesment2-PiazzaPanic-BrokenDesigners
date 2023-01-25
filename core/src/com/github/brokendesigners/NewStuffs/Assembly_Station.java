package com.github.brokendesigners.NewStuffs;

public class Assembly_Station {
    private Item[] items;
    private Item Product;
    private int Counter;
     

    public Assembly_Station()
    {
        this.items = new Item[3];
        this.items[0] = null;
        this.items[1] = null;
        this.items[2] = null;
        this.Product = null;
        this.Counter = 0;
    }
    //Overide storing products

    //return Product or spare ingredients
    public Item pickup()
    {
        Item Temp = this.items[0];
        this.items[0] = this.items[1];
        this.items[1] = this.items[2];
        this.items[2] = null;
        return  Temp;
    }

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
            return new Item(n);
        }
        else
        {
            return null;
        }

        
        
    }

    //Construct Product
    public void Construct()
    {
        String[] ItemStack = new String[]{this.items[0].getName(),this.items[1].getName(),this.items[2].getName()};
        if((this.items[0] != null)&(this.items[1] != null)&(this.items[2] != null))
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
                this.items[0] = this.Product;
                this.items[1] = null;
                this.items[2] = null;
                this.Product = null;
            }
            
            

        }
    }



}