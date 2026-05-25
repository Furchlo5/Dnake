package com.dnake.engine;

import com.dnake.entities.*;
import java.util.Random;
import java.util.Scanner;

public class Map {

    public static final int HEIGHT = 16;   // tüm sınıfların görebileceği bir sabit olması için static eklendi
    public static final int WIDTH = 32;    // böylece Map. ile erişebileceğiz
    private Snake snake;
    private Food food;

    public Map() {
        this.snake = new Snake();          // snake hafızada var edildi
        this.food = createRandomFood();    // yem oyun başında 1 kere oluşturuldu
    }

    // created Snake - food - map
    public void drawMap() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                String PartOfSnake = snake.checkSnakeAt(x, y);

                if (PartOfSnake != null) {
                    // snake
                    System.out.print(PartOfSnake);
                } else {
                    if (this.food != null && x == food.getX() && y == food.getY()) {
                        // food
                        System.out.print(food.getGameObjectType());
                    } else {
                        // map
                        if (x % 2 == 0) {
                            System.out.print(".");
                        } else {
                            System.out.print(" ");
                        }
                    }
                }
            }
            System.out.println("");
        }
    }

    // create a food in a random location
    public Food createRandomFood() {
        Random random_apple = new Random();
        int apple_x;
        int apple_y;

        do {
            apple_x = random_apple.nextInt(WIDTH);
            apple_y = random_apple.nextInt(HEIGHT);
        } while (snake.checkSnakeAt(apple_x, apple_y) != null);

        return new Food(apple_x, apple_y);

        /*  TODO
            yılanın boyu uzadıkça yemi boş alanda oluşturma olasılığı çok düşecek
            bu yüzden sonsuz döngüye girme ihtimali artacak
            
            çözüm: map sınıfında boş koordinatları bir ArrayList içerisinde tutarak
            food'umuzu o boş alandan seçtirebiliriz. böylece yılanın boyu ne kadar
            uzarsa uzasın boş bir alanı %100 bulacak bir algoritmamız olur. 
         */
    }

    // unendlich loop to move snake
    public void startGame() {

        /*
            TODO: yılan gittiği yönün tersine giderse kendi kendini yiyor.
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

            // if there is food, eat it.
            if (eatFood()) {
                food = createRandomFood();
                System.out.print(food.getGameObjectType());
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
}
