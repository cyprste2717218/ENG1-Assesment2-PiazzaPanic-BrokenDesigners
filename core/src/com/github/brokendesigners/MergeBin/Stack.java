//package com.github.brokendesigners.NewStuffs;
//
//public class Stack {
//
//    private SpecialItem top,middle,bottom;
//
//
//    public Stack()
//    {
//        this.top = null;
//        this.middle = null;
//        this.bottom = null;
//    }
//
//    //Push
//    public void pickup(SpecialItem Value)
//    {
//        //Only push if there is space to push, else do nothing
//        if(CheckBottom() == true)
//        {
//            if(Value != null)
//            {
//                this.bottom = this.middle;
//                this.middle = this.top;
//                this.top = Value;
//            }
//        }
//    }
//
//    //Pop
//    public SpecialItem drop()
//    {
//        SpecialItem temp = this.top;
//        this.top = this.middle;
//        this.middle = this.bottom;
//        this.bottom = null;
//        return temp;
//    }
//
//    //check if bottomless
//    private Boolean CheckBottom()
//    {
//        if(this.bottom == null)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
//
//
//}
