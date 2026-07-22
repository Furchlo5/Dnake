package com.dnake.engine;

import java.util.Random;
import java.util.Scanner;

import com.dnake.entities.*;
import com.dnake.interfaces.Spawnable;
import com.dnake.utils.EntitySpawner;

public class GameState {
    
    public static final int HEIGHT = 32;   // tüm sınıfların görebileceği bir sabit olması için static eklendi
    public static final int WIDTH = 32;    // böylece Map. ile erişebileceğiz
    private Snake snake;
    private Food food;
    private Poison poison;
    private boolean isGameOver = false;
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

    public void startGame() {
        // Klavye girdilerini asenkron (bağımsız) toplamak için yeni bir Thread açıyoruz
        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String input = sc.nextLine();

                    // 1. Durum: Eğer oyuncu 'p' tuşuna bastıysa oyunu pause et/çıkar
                    if (input.equalsIgnoreCase("p")) {
                        togglePause();
                    } 
                    // 2. Durum: Eğer oyun pause edilmemişse, gelen harfe göre yılanın yönünü değiştir
                    else if (!getIsPaused()) {
                        if (input.equals("w")) snake.setCurrentDirection(Direction.UP);
                        else if (input.equals("s")) snake.setCurrentDirection(Direction.DOWN);
                        else if (input.equals("a")) snake.setCurrentDirection(Direction.LEFT);
                        else if (input.equals("d")) snake.setCurrentDirection(Direction.RIGHT);
                    }
                }
            }
        });

        // Bu thread'i arka planda çalışması için tetikliyoruz!
        inputThread.start();

        // Giriş thread'ini başlattıktan sonra ana thread bu döngüye girer ve durmadan akar
        while (true) {
            // Ekranı temizleme ve haritayı çizme kodları
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // System.out.println("\n");
            // drawMap();

            if (!getIsPaused()) {
                // Yılan artık dışarıdan parametre almadan kendi içindeki yöne göre otomatik ilerliyor!
                snake.moveSnake(); 

                // // Çarpışma ve yeme kontrolleri
                // eatObject(poison);
                // if (eatObject(food)) {
                //     this.score += 10;
                //     createRandomFood();
                //     isPoisonRisiko();
                // }
            }

            // OYUNUN HIZI (Zaman Sayacı): Yılanın saniyede kaç kare gideceğini belirler.
            // Thread'i 200 milisaniye uyutarak döngüyü yavaşlatıyoruz, yoksa yılan ışık hızında gider!
            try {
                Thread.sleep(200); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
}

