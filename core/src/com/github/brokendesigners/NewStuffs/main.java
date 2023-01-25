package com.github.brokendesigners.NewStuffs;

import com.github.brokendesigners.MergeBin.Counter_Station;

class main
{
    // public static Item CuttingStuff(String a)
    // {
    //     Item x = new Item(a);
    //     System.out.println(x.getName());
    //     x.SetStation("Cutting_Station");
    //     x.Cutting();
    //     System.out.println(x.getName());
    //     return x;
    // }

    // public static Item[] TestCutting()
    // {
    //     Item x = CuttingStuff("Tomato");
    //     Item y = CuttingStuff("Lettuce");
    //     Item z = CuttingStuff("Onion");
        
    //     Assemly_Station MakeSalad = new Assemly_Station();
    //     System.out.println(MakeSalad.getProduct());
    //     MakeSalad.setItem(x, 1);
    //     MakeSalad.setItem(y, 2);
    //     MakeSalad.setItem(z, 0);
    //     MakeSalad.Construct();
    //     System.out.println(MakeSalad.getProduct().getName());
    //     Item[] lis = {x,y,z};
    //     return lis;
    // }

    // public void TestStack()
    // {
    //     Item[] items = TestCutting();
    //     Stack heldItems = new Stack();
    //     System.out.println();
    //     heldItems.Push(items[0]);
    //     heldItems.Push(items[1]);
    //     heldItems.Push(items[2]);
    //     heldItems.Push(items[0]);
    //     heldItems.Pop();
    //     heldItems.Pop();
    //     heldItems.Push(items[1]);
    //     heldItems.Push(items[2]);
    //     heldItems.Pop();
    //     heldItems.Pop();
    //     heldItems.Push(items[1]);
    // }

    public static void MakeCorrectSalad()
    {
        //Make Dispensers
        //Salad Ingredients
        Dispenser Dis_Tomato = new Dispenser("Tomato");
        Dispenser Dis_Lettuce = new Dispenser("Lettuce");
        Dispenser Dis_Onion = new Dispenser("Onion");

        //Create Stations
        AssemblyStation Sta_Assembly = new AssemblyStation();
        Baking_Station Sta_Baking = new Baking_Station();
        Cooking_Station Sta_Cooking = new Cooking_Station();
        Counter_Station Sta_Counter1 = new Counter_Station();
        Counter_Station Sta_Counter2 = new Counter_Station();
        Counter_Station Sta_Counter3 = new Counter_Station();
        Counter_Station Sta_Counter4 = new Counter_Station();
        Cutting_Station Sta_Cutting = new Cutting_Station();

        //Create hand
        Stack hand = new Stack();
        //----------------------------------------------------
        //Scenario 1: Make salad
        hand.pickup(Dis_Tomato.Interact()); //Collecting all the ingredients at once
        hand.pickup(Dis_Lettuce.Interact());
        hand.pickup(Dis_Onion.Interact());

        Sta_Counter1.StoreItem(hand.drop()); //Storing top 2 from stack so that we can process last one
        Sta_Counter2.StoreItem(hand.drop());

        //Going to cut ingredients
        Sta_Cutting.StoreItem(hand.drop());
        Sta_Cutting.Interact();
        hand.pickup(Sta_Cutting.pickup());
        Sta_Counter3.StoreItem(hand.drop());

        hand.pickup(Sta_Counter1.pickup());
        Sta_Cutting.StoreItem(hand.drop());
        Sta_Cutting.Interact();
        hand.pickup(Sta_Cutting.pickup());
        Sta_Counter1.StoreItem(hand.drop());

        hand.pickup(Sta_Counter2.pickup());
        Sta_Cutting.StoreItem(hand.drop());
        Sta_Cutting.Interact();
        hand.pickup(Sta_Cutting.pickup());
        Sta_Counter2.StoreItem(hand.drop());

        //Repickup items
        hand.pickup(Sta_Counter1.pickup());
        hand.pickup(Sta_Counter2.pickup());
        hand.pickup(Sta_Counter3.pickup());

        //Onto Assemble a salad at assembly
        Sta_Assembly.StoreItem(hand.drop());
        Sta_Assembly.StoreItem(hand.drop());
        Sta_Assembly.StoreItem(hand.drop());

        //Assemble
        Sta_Assembly.Construct();

        //pickup product
        hand.pickup(Sta_Assembly.pickup());
        System.out.println("Yes--------");
        System.out.println(hand.drop().getName());

    }

    public static void MakeCorrectBurger()
    {
         //Burger Ingredients
         Dispenser Dis_Meat = new Dispenser("Meat");
         Dispenser Dis_Bun = new Dispenser("Bun");
 
         //Create Stations
         AssemblyStation Sta_Assembly = new AssemblyStation();
         Baking_Station Sta_Baking = new Baking_Station();
         Cooking_Station Sta_Cooking = new Cooking_Station();
         Counter_Station Sta_Counter1 = new Counter_Station();
         Counter_Station Sta_Counter2 = new Counter_Station();
         Counter_Station Sta_Counter3 = new Counter_Station();
         Counter_Station Sta_Counter4 = new Counter_Station();
         Cutting_Station Sta_Cutting = new Cutting_Station();

         //Create hand
        Stack hand = new Stack();
        //----------------------------------------------------
        //Scenario 2: Make Burger
        hand.pickup(Dis_Bun.Interact());
        hand.pickup(Dis_Bun.Interact());
        hand.pickup(Dis_Meat.Interact());

        //cook meat into burger
        Sta_Counter1.StoreItem(hand.drop());
        Sta_Counter1.formPatty(); //turns meat into patty
        hand.pickup(Sta_Counter1.pickup());
        Sta_Cooking.StoreItem(hand.drop());
        Sta_Cooking.Interact();
        Sta_Cooking.Flipping();
        hand.pickup(Sta_Cooking.pickup());
        Sta_Counter1.StoreItem(hand.drop());

        //toasting buns
        Sta_Cooking.StoreItem(hand.drop());
        Sta_Cooking.Interact();
        hand.pickup(Sta_Cooking.pickup());
        Sta_Counter2.StoreItem(hand.drop());
        Sta_Cooking.StoreItem(hand.drop());
        Sta_Cooking.Interact();
        hand.pickup(Sta_Cooking.pickup());
        hand.pickup(Sta_Counter2.pickup());

        //Pickup burger
        hand.pickup(Sta_Counter1.pickup());
        
        //Onto Assemble a burger at assembly
        Sta_Assembly.StoreItem(hand.drop());
        Sta_Assembly.StoreItem(hand.drop());
        Sta_Assembly.StoreItem(hand.drop());

        //Assemble
        Sta_Assembly.Construct();

        //pickup product
        hand.pickup(Sta_Assembly.pickup());
        System.out.println("Yes--------");
        System.out.println(hand.drop().getName());

    }
   
    public static void MakeBadBurger()
    {
         //Burger Ingredients
         Dispenser Dis_Meat = new Dispenser("Meat");
         Dispenser Dis_Bun = new Dispenser("Bun");
 
         //Create Stations
         AssemblyStation Sta_Assembly = new AssemblyStation();
         Baking_Station Sta_Baking = new Baking_Station();
         Cooking_Station Sta_Cooking = new Cooking_Station();
         Counter_Station Sta_Counter1 = new Counter_Station();
         Counter_Station Sta_Counter2 = new Counter_Station();
         Counter_Station Sta_Counter3 = new Counter_Station();
         Counter_Station Sta_Counter4 = new Counter_Station();
         Cutting_Station Sta_Cutting = new Cutting_Station();

         //Create hand
        Stack hand = new Stack();
        //----------------------------------------------------
        //Scenario 3: Make Bad Burger
        hand.pickup(Dis_Bun.Interact());
        hand.pickup(Dis_Bun.Interact());
        hand.pickup(Dis_Meat.Interact());
        Sta_Assembly.StoreItem(hand.drop());
        Sta_Assembly.StoreItem(hand.drop());
        Sta_Assembly.StoreItem(hand.drop());

        //Assemble
        Sta_Assembly.Construct();

        //pickup product
        hand.pickup(Sta_Assembly.pickup());
        hand.pickup(Sta_Assembly.pickup());
        hand.pickup(Sta_Assembly.pickup());
        System.out.println("Yes--------");
        System.out.println(hand.drop().getName());
    }

    public static void main(String[] args) {
        MakeCorrectSalad();
        MakeCorrectBurger();
        MakeBadBurger();


    }
}