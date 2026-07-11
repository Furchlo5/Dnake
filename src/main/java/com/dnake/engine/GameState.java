package com.dnake.engine;

import com.dnake.entities.Direction;
import com.dnake.entities.Food;
import com.dnake.entities.Poison;
import com.dnake.entities.Snake;

public class GameState {
    private Snake snake;
    private Food food;
    private Poison poison;
    private boolean isPaused = false;
    private boolean isGameOver = false;

    public GameState() {
        // Oyun başladığında yılanı ve ilk yemi oluştur
        this.snake = new Snake();
        this.food = new Food(10, 10); // Şimdilik test için sabit bir yere koyalım
        // İleride kendi EntitySpawner'ını buraya dahil edebilirsin
    }

    // Oyun döngüsü (GameLoopThread) her 200ms'de bir bu metodu çağıracak
    public void update(String input) {
        if (isPaused || isGameOver) return; // Oyun durduysa hiçbir şeyi güncelleme

        // Gelen girdiye göre yılanın yönünü güncelle
        if (input != null) {
            if (input.equals("w")) snake.setCurrentDirection(Direction.UP);
            else if (input.equals("s")) snake.setCurrentDirection(Direction.DOWN);
            else if (input.equals("a")) snake.setCurrentDirection(Direction.LEFT);
            else if (input.equals("d")) snake.setCurrentDirection(Direction.RIGHT);
            else if (input.equals("p")) isPaused = !isPaused;
        }

        // Yılanı o anki yönünde bir adım yürüt
        snake.moveSnake();

        // TODO: Çarpışma ve elma yeme mantığını (eatObject) buraya taşıyacağız
    }

    // Çizim yapabilmesi için GamePanel'e verileri açıyoruz (Getter metodları)
    public Snake getSnake() { return snake; }
    public Food getFood() { return food; }
    public Poison getPoison() { return poison; }
    public boolean isPaused() { return isPaused; }
}

