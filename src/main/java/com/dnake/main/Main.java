// TODO: yeni bir package keyword'u kullanıldığında path doğru olmasına rağmen IDE kırmızı yakıyor.

package com.dnake.main;

import com.dnake.engine.Map;

public class Main {
    // snake -> oooooox

    public static void main(String[] args) {
        Map gameMap = new Map();
        gameMap.startGame();
    }
}
