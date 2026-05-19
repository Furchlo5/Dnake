package com.dnake.entities;

public class SnakeLocation extends GameObject {

    public SnakeLocation(int x, int y) {
        super(x, y);
    }

    @Override
    public String getGameObjectType() {
        return "SnakePart";     // kullanılmayacak, sadece yapıyı bozmasın diye konuldu
    }
}
