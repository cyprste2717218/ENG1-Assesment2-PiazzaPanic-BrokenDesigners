package com.github.brokendesigners.NewStuffs;

public abstract class Station {
    private String station_name;
    public Item stored;
    public Boolean storing;

    public Station(String n)
    {
        this.station_name = n;
        this.stored = null;
        this.storing = false;
    }

    public void StoreItem(Item x) //To hold item on a counter
    {
        this.stored = x;
        this.storing = true;
    }
    public Item pickup() //Picking up from counter
    {
        Item Temp = this.stored;
        this.stored = null;
        this.storing = false;
        return Temp;
    }

    //Check if operation can be completed
    public Boolean Applicable(String[] Conditions,String state, String itemName)
    {
        if(this.station_name == state) //Checks if its in the correct station before preforming
        {
            for(int i = 0;i<Conditions.length;i++)
            {
                if(Conditions[i] == itemName)
                {
                    return true;
                }
            }
        }
        return false;
    }


}
