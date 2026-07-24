package com.dnake.engine;

import java.util.Random;
import java.util.Scanner;

import com.dnake.entities.*;
import com.dnake.interfaces.*;
import com.dnake.threads.GameLoopThread;
import com.dnake.utils.EntitySpawner;

public class GameState {
    
    public static final int HEIGHT = 32;   // tüm sınıfların görebileceği bir sabit olması için static eklendi
    public static final int WIDTH = 32;    // böylece Map. ile erişebileceğiz
    private Snake snake;
    private Food food;
    private Poison poison;
    private volatile boolean isGameOver = false;
    private EntitySpawner<Food> foodSpawner = new EntitySpawner<>();
    private EntitySpawner<Poison> poisonSpawner = new EntitySpawner<>();
    Random random_number = new Random();

    // Oyunun duraklatılıp duraklatılmadığını tutar.
    // Farklı thread'ler anlık görebilsin diye volatile yapıyoruz.
    private volatile boolean isPaused = false;

    public GameState() {
        // Oyun başladığında yılanı ve ilk yemi oluştur
        this.snake = new Snake();
        createRandomFood();
        isPoisonRisiko();
    }

    // Çizim yapabilmesi için GamePanel'e verileri açıyoruz (Getter metodları)
    public Snake getSnake() { 
        return snake; 
    }
    public Food getFood() { 
        return food;
    }
    public Poison getPoison() { 
        return poison; 
    }
    public boolean getIsPaused() { 
        return isPaused; 
    }

    // Oyun döngüsü (GameLoopThread) her 200ms'de bir bu metodu çağıracak
    public void update(String input) {

        // 1. ÖNCE PAUSE KONTROLÜ (Oyun durdurulmuş olsa bile 'P' tuşunun okunabilmesi gerekir!)
        if (input != null && input.equalsIgnoreCase("p")) {
            isPaused = !isPaused;
            return; // Pause durumunu değiştirdik, bu tur için aşağıdaki hareket kodlarını çalıştırma
        }
        if (isPaused || isGameOver) return; // Oyun durduysa hiçbir şeyi güncelleme

        // Gelen girdiye göre yılanın yönünü güncelle
        if (input != null) {
            if (input.equals("w") && snake.getCurrentDirection() != Direction.DOWN) snake.setCurrentDirection(Direction.UP);
            else if (input.equals("s") && snake.getCurrentDirection() != Direction.UP) snake.setCurrentDirection(Direction.DOWN);
            else if (input.equals("a") && snake.getCurrentDirection() != Direction.RIGHT) snake.setCurrentDirection(Direction.LEFT);
            else if (input.equals("d") && snake.getCurrentDirection() != Direction.LEFT) snake.setCurrentDirection(Direction.RIGHT);
        }

        // Yılanı o anki yönünde bir adım yürüt
        snake.moveSnake();

        if (eatObject(poison)) {
            isGameOver = true;
        }

        if (eatObject(food)) {
            createRandomFood();

            // yılanın hızlanma mantığı
            if (GameLoopThread.speed <= 200 && GameLoopThread.speed >= 150) {
                GameLoopThread.speed -= 10;
            } else if (GameLoopThread.speed < 150 && GameLoopThread.speed >= 100) {
                GameLoopThread.speed -= 5;
            } else if (GameLoopThread.speed < 100 && GameLoopThread.speed >= 50) {
                GameLoopThread.speed -= 2;
            } else if (GameLoopThread.speed < 50 && GameLoopThread.speed > 30) {
                GameLoopThread.speed -= 1;
            } else if (GameLoopThread.speed <= 30) {
                // artık hızımız sabit
            } 
            
            // elma yedigimiz icin map'e %20 ihtimalle poison firlat
            isPoisonRisiko();
        }
        
    }

    public void togglePause() {
        this.isPaused = !this.isPaused;
        if (this.isPaused) {
            System.out.println("--- OYUN DURAKLATILDI (Devam etmek için tekrar 'P' basın) ---");
        } else {
            System.out.println("--- OYUN DEVAM EDİYOR ---");
        }
    }

    public void createRandomFood(){
        // ai yazdı
        this.food = foodSpawner.createRandomObject(snake, new Spawnable<Food>() {
            @Override
            public Food spawn(int x, int y){
                return new Food(x, y);
            }
        });
    }
    
    public void isPoisonRisiko() {
    // her food yenildiginde önceki poison'i temizle
    this.poison = null;
    int poisonRisk = random_number.nextInt(5); // 0 ile 4 arası sayı üretir (%20 ihtimal)
    
        if (poisonRisk == 1) {
            this.poison = poisonSpawner.createRandomObject(snake, new Spawnable<Poison>() {
                @Override
                public Poison spawn(int x, int y){
                    return new Poison(x, y);
                }
            });
        }
    }

    public <T extends GameObject & IConsumable> boolean eatObject(T item){
            if (item == null) return false;
    
            int snakeHead_x = snake.getSnakeLocation().get(0).getX();
            int snakeHead_y = snake.getSnakeLocation().get(0).getY();
    
            if (snakeHead_x == item.getX() && snakeHead_y == item.getY()) {
                item.consume(snake);
                return true;
            }
            return false;
        }
}

