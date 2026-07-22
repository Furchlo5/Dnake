package com.dnake.entities;

import com.dnake.engine.GameState;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

    private final int SNAKE_MAX_WIDTH_POSITION = 12;
    private final int SNAKE_MIN_WIDTH_POSITION = 6;
    private final int SNAKE_MAX_HEIGHT_POSITION = 6;
    private final int SNAKE_MIN_HEIGHT_POSITION = 3;

    // Yılanın anlık yönünü tutan değişken. 
    // volatile direkt main memory'e veriyi yazar. Böylece ileride başka bir thread bu değişkeni okuyacağı zaman cache incoherence önlenmiş olur. 
    // kısaca multi-threading sağlar
    private volatile Direction currentDirection = Direction.LEFT;  

    private ArrayList<SnakeLocation> snakelocation = new ArrayList();
    private SnakeLocation last_removed_SnakeLocation = null;

    // oyununu kenarlara yakın başlaması mantıksız olduğu için yılan orta kısımda olusur
    public Snake() {
        Random createRandom = new Random();

        // mapin ortalarında random x ve y değerleri 
        int randon_x = createRandom.nextInt(GameState.WIDTH - SNAKE_MAX_WIDTH_POSITION) + SNAKE_MIN_WIDTH_POSITION;
        int random_y = createRandom.nextInt(GameState.HEIGHT - SNAKE_MAX_HEIGHT_POSITION) + SNAKE_MIN_HEIGHT_POSITION;

        // create a snake position to initialize the snake
        SnakeLocation sl1 = new SnakeLocation(randon_x, random_y);
        snakelocation.add(sl1);  // 0. index yani kafa

        SnakeLocation sl2 = new SnakeLocation(randon_x + 1, random_y);
        snakelocation.add(sl2);

        SnakeLocation sl3 = new SnakeLocation(randon_x + 2, random_y);
        snakelocation.add(sl3);

    }

    public SnakeLocation getLastRemovedSnaleLocation() {
        return last_removed_SnakeLocation;
    }

    public ArrayList<SnakeLocation> getSnakeLocation() {
        return snakelocation;
    }

    /* 
        burada normalde for-each kullanacaktım ama
        bize bulduğumuz elemanın index numarası lazım.
        indexof() index numarasını bulmak için arka planda tekrardan döngüyü çalıştırır ve
        bu performans açısından yorucudur.
     */
    public String checkSnakeAt(int x, int y) {
        for (int i = 0; i < snakelocation.size(); i++) {
            SnakeLocation snkloc = snakelocation.get(i);

            if (x == snkloc.getX() && y == snkloc.getY()) {
                if (i == 0) {
                    // 0. index -> baş
                    return "x";
                } else {
                    // diğer indexler -> kuyruk
                    return "o";
                }
            }
        }

        // döngüden çıktı ve hiçbir eşleşme yok -> yılana ait koordinat değil
        return null;
    }

    public void moveSnake() {

        int snake_x;
        int snake_y;

        snake_x = snakelocation.get(0).getX();
        snake_y = snakelocation.get(0).getY();
        

        if (currentDirection == Direction.UP && snake_y > 0) {
            SnakeLocation slw = new SnakeLocation(snake_x, snake_y - 1);

            // yılan kendini yerse game over, yemezse de yeni konumu ekle
            isCollidingWithBody(slw);

            // yılanin elma yeme ihtimaline karsi kuyruk hafizada tutularak siliniyor
            popTailPositon();
        } else if (currentDirection == Direction.UP && snake_y == 0) {
            SnakeLocation slw = new SnakeLocation(snake_x, GameState.HEIGHT - 1);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (currentDirection == Direction.LEFT && snake_x > 0) {
            SnakeLocation slw = new SnakeLocation(snake_x - 1, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (currentDirection == Direction.LEFT && snake_x == 0) {
            SnakeLocation slw = new SnakeLocation(GameState.WIDTH - 1, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (currentDirection == Direction.DOWN && snake_y < GameState.HEIGHT - 1) {
            SnakeLocation slw = new SnakeLocation(snake_x, snake_y + 1);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (currentDirection == Direction.DOWN && snake_y == GameState.HEIGHT - 1) {
            SnakeLocation slw = new SnakeLocation(snake_x, 0);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (currentDirection == Direction.RIGHT && snake_x < GameState.WIDTH - 1) {
            SnakeLocation slw = new SnakeLocation(snake_x + 1, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (currentDirection == Direction.RIGHT && snake_x == GameState.WIDTH - 1) {
            SnakeLocation slw = new SnakeLocation(0, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else {
            // ignore
        }
    }

    public void popTailPositon(){
        last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
        snakelocation.remove(snakelocation.size() - 1);
    }

    public void isCollidingWithBody(SnakeLocation slw) {
        String is_eat_itself = checkSnakeAt(slw.getX(), slw.getY());
        if (is_eat_itself == null) {
            snakelocation.add(0, slw);
        } else {
            System.out.println("GAME OVER");
            System.exit(0);
        }
    }

    // public void grow() {
    //     if (last_removed_SnakeLocation != null) {
    //         snakelocation.add(last_removed_SnakeLocation);
    //     }
    // }

    public Direction getCurrentDirection() {
    return currentDirection;
    }

    public void setCurrentDirection(Direction newDirection) {
    // TODO: Yılanın kendi içine doğru (tersine) dönmesini engelleme mantığını buraya kurabilirsin!
    this.currentDirection = newDirection;
    }
}
