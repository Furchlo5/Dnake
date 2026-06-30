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

    // infinite loop to move the snake
    public void startGame(){

        /*
            TODO: yılan gittiği yönün tersine giderse kendi kendini yiyor. Biz bunu ignorelamasını istiyoruz.
            çözüm: yılan kendini mi yiyor kontrolünü yaparken eğer yediği parça yılanın 1. indexi ise bunu ignorlayabiliriz çünkü yılan 1. indexteki parçayı min 3 hamlede yiyebilir. 1 hamlede yediği durumu ignorlamış oluruz böylece. 
        */
        Scanner sc = new Scanner(System.in);
        String richtungseingabe;

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("\n");

            drawMap();
    
            System.out.print("\nMove (w/a/s/d/q): ");
            richtungseingabe = sc.nextLine();
            snake.moveSnake(richtungseingabe);

            // zehirli yemek kontrolu
            eatObject(poison);

            // if there is food, eat it.
            if (eatObject(food)) {
                this.score += 10;
                createRandomFood();

                // elma yedigimiz icin map'e %20 ihtimalle poison firlat
                isPoisonRisiko();
            }
        }
    }

    // public boolean eatFood() {
    //     int snakeHead_x = snake.getSnakeLocation().get(0).getX();
    //     int snakeHead_y = snake.getSnakeLocation().get(0).getY();

    //     if (snakeHead_x == food.getX() && snakeHead_y == food.getY()) {
    //         food.consume(snake);
    //         return true;
    //     }
    //     return false;
    // }

    // // TODO: kod tekrarı
    // public boolean eatPoison(){
    //     if (this.poison == null) return false;

    //     int snakeHead_x = snake.getSnakeLocation().get(0).getX();
    //     int snakeHead_y = snake.getSnakeLocation().get(0).getY();

    //     if (snakeHead_x == poison.getX() && snakeHead_y == poison.getY()) {
    //         poison.consume(snake);
    //         return true;
    //     }
    //     return false;
    // }

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
}



