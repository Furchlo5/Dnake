package com.dnake.engine;

import com.dnake.entities.*;
import com.dnake.interfaces.IConsumable;
import com.dnake.interfaces.Spawnable;
import com.dnake.utils.EntitySpawner;
import java.util.Scanner;
import java.util.Random;

public class Map {

    public static final int HEIGHT = 16;   // tüm sınıfların görebileceği bir sabit olması için static eklendi
    public static final int WIDTH = 32;    // böylece Map. ile erişebileceğiz
    private int score = 0;
    private Snake snake;
    private Food food;
    private Poison poison;
    private EntitySpawner<Food> foodSpawner = new EntitySpawner<>();
    private EntitySpawner<Poison> poisonSpawner = new EntitySpawner<>();

    // Oyunun duraklatılıp duraklatılmadığını tutar.
    // Farklı thread'ler anlık görebilsin diye volatile yapıyoruz.
    private volatile boolean isPaused = false;

    public Map() {
        // snake hafızada var edildi
        this.snake = new Snake();          

        // yem oyun başında 1 kere oluşturuldu  
        createRandomFood();

        // food örnek alınarak oyun başında %20 olasılıkla poison oluşturuluyor.
        isPoisonRisiko();
    }

    // created Snake - food - map
    public void drawMap() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                String PartOfSnake = snake.checkSnakeAt(x, y);

                // snake
                if (PartOfSnake != null) {
                    System.out.print(PartOfSnake);
                } 
                // food
                else if (this.food != null && x == food.getX() && y == food.getY()){
                    System.out.print(food.getGameObjectType());
                } 
                // poison
                else if (this.poison != null && x == poison.getX() && y == poison.getY()) {
                    System.out.print(poison.getGameObjectType());
                } 
                // map
                else {
                    if (x % 2 == 0) {
                        System.out.print(".");
                    } else {
                        System.out.print(" ");
                    }
                }   
            }
            System.out.println("");
        }
        System.out.println("\nScore: " + this.score);
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
                    else if (!isPaused()) {
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
            System.out.println("\n");
            drawMap();

            if (!isPaused()) {
                // Yılan artık dışarıdan parametre almadan kendi içindeki yöne göre otomatik ilerliyor!
                snake.moveSnake(); 

                // Çarpışma ve yeme kontrolleri
                eatObject(poison);
                if (eatObject(food)) {
                    this.score += 10;
                    createRandomFood();
                    isPoisonRisiko();
                }
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

    // kod tekrarı ai yardımıyle çözüldü -> generic class
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

    

    public void isPoisonRisiko() {
        // her food yenildiginde önceki poison'i temizle
        this.poison = null;

        Random random_number = new Random();
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
    

    public void createRandomFood(){
        // ai yazdı
        this.food = foodSpawner.createRandomObject(snake, new Spawnable<Food>() {
            @Override
            public Food spawn(int x, int y){
                return new Food(x, y);
            }
        });
    }
    
    public boolean isPaused() {
    return isPaused;
}

    public void togglePause() {
        this.isPaused = !this.isPaused;
        if (this.isPaused) {
            System.out.println("--- OYUN DURAKLATILDI (Devam etmek için tekrar 'P' basın) ---");
        } else {
            System.out.println("--- OYUN DEVAM EDİYOR ---");
        }
    }

    
}



