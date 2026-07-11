package com.dnake.threads;

import com.dnake.engine.Map;
import java.util.Scanner;

public class InputListenerThread implements Runnable {
    private final InputQueue queue;
    private final Map gameMap;

    // Constructor: Dinleyiciye, harfleri atacağı kuyruğu ve pause için haritayı veriyoruz
    public InputListenerThread(InputQueue queue, Map gameMap) {
        this.queue = queue;
        this.gameMap = gameMap;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            // Thread burada klavyeden Enter'a basılana kadar bekler (Blocklanır)
            String input = sc.nextLine();
            
            // Eğer oyuncu 'P' tuşuna bastıysa, doğrudan Map üzerinden oyunu duraklat/devam ettir
            if (input.equalsIgnoreCase("p")) {
                gameMap.togglePause();
            } 
            // P dışında geçerli bir yön tuşuna basıldıysa (w, a, s, d), bunu kuyruğa fırlat
            else if (input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")) {
                queue.addInput(input);
            }
        }
    }
}
