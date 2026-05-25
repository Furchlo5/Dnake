package com.dnake.engine;

import com.dnake.entities.*;
import com.dnake.interfaces.Spawnable;
import com.dnake.utils.EntitySpawner;
import java.util.Scanner;
import java.util.Random;

public class Map {

    public static final int HEIGHT = 16;   // tüm sınıfların görebileceği bir sabit olması için static eklendi
    public static final int WIDTH = 32;    // böylece Map. ile erişebileceğiz
    private Snake snake;
    private Food food;
    private Poison poison;
    private EntitySpawner<Food> foodSpawner = new EntitySpawner<>();
    private EntitySpawner<Poison> poisonSpawner = new EntitySpawner<>();

    public Map() {
        this.snake = new Snake();          // snake hafızada var edildi

        // ai tarafından yazıldı.
        this.food = foodSpawner.createRandomObject(snake, new Spawnable<Food>() {
            @Override
            public Food spawn(int x, int y){
                return new Food(x, y);
            }
        });    // yem oyun başında 1 kere oluşturuldu
    }

    // created Snake - food - map
    public void drawMap() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                String PartOfSnake = snake.checkSnakeAt(x, y);

                // snake
                if (PartOfSnake != null) {
                    System.out.print(PartOfSnake);
                } else if (this.food != null && x == food.getX() && y == food.getY()){
                    // food
                    System.out.print(food.getGameObjectType());
                } else if (this.poison != null && x == poison.getX() && y == poison.getY()) {
                    // poison
                    System.out.print(poison.getGameObjectType());
                } else {
                    // map
                    if (x % 2 == 0) {
                        System.out.print(".");
                    } else {
                        System.out.print(" ");
                    }
                }   
            }
            System.out.println("");
        }
    }

    // infinite loop to move the snake
    public void startGame() {

        /*
            TODO: yılan gittiği yönün tersine giderse kendi kendini yiyor. Biz bunu ignorelamasını istiyoruz.
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
            eatPoison();

            // if there is food, eat it.
            if (eatFood()) {
                // ai çözümü
                food = foodSpawner.createRandomObject(snake, new Spawnable<Food>() {
                    @Override
                    public Food spawn(int x, int y){
                        return new Food(x, y);
                    }
                });
                // System.out.print(food.getGameObjectType());

                isPoisonRisiko();
            }
        }
    }

    public boolean eatFood() {
        int snakeHead_x = snake.getSnakeLocation().get(0).getX();
        int snakeHead_y = snake.getSnakeLocation().get(0).getY();

        if (snakeHead_x == food.getX() && snakeHead_y == food.getY()) {
            food.consume(snake);
            return true;
        }
        return false;
    }

    // TODO: kod tekrarı
    public boolean eatPoison(){
        if (this.poison == null) return false;

        int snakeHead_x = snake.getSnakeLocation().get(0).getX();
        int snakeHead_y = snake.getSnakeLocation().get(0).getY();

        if (snakeHead_x == poison.getX() && snakeHead_y == poison.getY()) {
            poison.consume(snake);
            return true;
        }
        return false;
    }

    public void isPoisonRisiko() {
        Random random_number = new Random();
        int poisonRisk = random_number.nextInt(10); // 0 ile 9 arası sayı üretir (%10 ihtimal)
        
        if (poisonRisk == 0) {
            this.poison = poisonSpawner.createRandomObject(snake, new Spawnable<Poison>() {
                @Override
                public Poison spawn(int x, int y){
                    return new Poison(x, y);
                }
            });
        }
    }
        
}



