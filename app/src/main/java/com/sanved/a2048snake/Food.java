package com.sanved.a2048snake;


public class Food {

    int posi, weight;

    Food(int posi, int weight) {
        this.posi = posi;
        this.weight = weight;
    }

    public int getPosi() {
        return posi;
    }

    public int getWeight() {
        return weight;
    }
}
