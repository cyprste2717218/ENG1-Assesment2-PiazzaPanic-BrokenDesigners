package com.github.brokendesigners.NewStuffs;

public abstract class Station {
    private String station_name;
    public SpecialItem hand;
    public Boolean storing;

    public Station(String n)
    {
        this.station_name = n;
        this.hand = null;
        this.storing = false;
    }

    public void StoreItem(SpecialItem x) //To hold item on a counter
    {
        this.hand = x;
        this.storing = true;
    }
    public SpecialItem pickup() //Picking up from counter
    {
        SpecialItem Temp = this.hand;
        this.hand = null;
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
