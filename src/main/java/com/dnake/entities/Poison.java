package com.dnake.entities;

import com.dnake.interfaces.IConsumable;

public class Poison extends GameObject implements IConsumable {

    public Poison(int x, int y) {
        super(x, y);
    }

    @Override
    public String getGameObjectType() {
        return "?";
    }

    @Override
    public void consume(Snake snake) {
        System.out.println("GAME OVER");
    }
}
