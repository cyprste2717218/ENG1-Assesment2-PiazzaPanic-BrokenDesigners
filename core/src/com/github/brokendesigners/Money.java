package com.github.brokendesigners;


public class Money  {

    public float totalMoney;




    public Money() {
        this.totalMoney = 0;
    }


    public void addMoney(float value) {
        this.totalMoney += value;
    }

    public void subtractMoney(float value) {
        this.totalMoney -= value;
    }

}


