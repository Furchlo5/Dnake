package com.dnake.entities;

public class Food extends GameObject {

    public Food(int x, int y) {
        super(x, y);
    }

    @Override
    public String getGameObjectType() {
        return "@";
    }

}
