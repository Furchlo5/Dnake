package com.dnake.entities;

import com.dnake.engine.Map;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

    private final int SNAKE_MAX_WIDTH_POSITION = 12;
    private final int SNAKE_MIN_WIDTH_POSITION = 6;
    private final int SNAKE_MAX_HEIGHT_POSITION = 6;
    private final int SNAKE_MIN_HEIGHT_POSITION = 3;

    private ArrayList<SnakeLocation> snakelocation = new ArrayList();
    private SnakeLocation last_removed_SnakeLocation = null;

    // oyununu kenarlara yakın başlaması mantıksız olduğu için yılan orta kısımda olusur
    public Snake() {
        Random createRandom = new Random();

        // mapin ortalarında random x ve y değerleri 
        int randon_x = createRandom.nextInt(Map.WIDTH - SNAKE_MAX_WIDTH_POSITION) + SNAKE_MIN_WIDTH_POSITION;
        int random_y = createRandom.nextInt(Map.HEIGHT - SNAKE_MAX_HEIGHT_POSITION) + SNAKE_MIN_HEIGHT_POSITION;

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

    public void moveSnake(String richtungseingabe) {
        /*
            w -> up arrow
            a -> left arrow
            s -> down arrow
            d -> right arrow
            q -> exit
         */

        int snake_x;
        int snake_y;

        snake_x = snakelocation.get(0).getX();
        snake_y = snakelocation.get(0).getY();
        

        if (richtungseingabe.equals("w") && snake_y > 0) {
            SnakeLocation slw = new SnakeLocation(snake_x, snake_y - 1);

            // yılan kendini yerse game over, yemezse de yeni konumu ekle
            isCollidingWithBody(slw);

            // yılanin elma yeme ihtimaline karsi kuyruk hafizada tutularak siliniyor
            popTailPositon();
        } else if (richtungseingabe.equals("w") && snake_y == 0) {
            SnakeLocation slw = new SnakeLocation(snake_x, Map.HEIGHT - 1);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("a") && snake_x > 0) {
            SnakeLocation slw = new SnakeLocation(snake_x - 1, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("a") && snake_x == 0) {
            SnakeLocation slw = new SnakeLocation(Map.WIDTH - 1, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("s") && snake_y < Map.HEIGHT - 1) {
            SnakeLocation slw = new SnakeLocation(snake_x, snake_y + 1);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("s") && snake_y == Map.HEIGHT - 1) {
            SnakeLocation slw = new SnakeLocation(snake_x, 0);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("d") && snake_x < Map.WIDTH - 1) {
            SnakeLocation slw = new SnakeLocation(snake_x + 1, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("d") && snake_x == Map.WIDTH - 1) {
            SnakeLocation slw = new SnakeLocation(0, snake_y);
            isCollidingWithBody(slw);
            popTailPositon();
        } else if (richtungseingabe.equals("q")) {
            System.out.println("Oyun sona erdirildi...");
            System.exit(0);
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

    public void grow() {
        if (last_removed_SnakeLocation != null) {
            snakelocation.add(last_removed_SnakeLocation);
        }
    }
}
