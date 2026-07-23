package com.dnake.entities;

import com.dnake.interfaces.IConsumable;

public class Food extends GameObject implements IConsumable { 
    
    public Food(int x, int y) {
        super(x, y);
    }

    @Override
    public String getGameObjectType() {
        return "0";
    }

    @Override
    public void consume(Snake snake) {
        snake.grow();
    }
}
