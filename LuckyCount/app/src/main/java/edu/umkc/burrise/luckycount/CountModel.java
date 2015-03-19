package edu.umkc.burrise.luckycount;

public class CountModel {
    // State
    private int count = 0;
    private int [] unluckyNumbers = {4,5,6,13};

    public int getValue(){
        return count;
    }

    // Business logic
    public void increment() {
        count++;
    }

    // Business logic
    public void decrement() {
        count--;
    }

    // Business logic
    public boolean isLucky() {
        for(int i = 0; i < unluckyNumbers.length; i++)
            if (count == unluckyNumbers[i])
                return false;
        return true;
    }
}
