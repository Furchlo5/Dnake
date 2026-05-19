package com.dnake.entities;

import com.dnake.engine.Map;
import java.util.ArrayList;
import java.util.Random;

public class Snake {

    // private int snakeLength;
    private ArrayList<SnakeLocation> snakelocation = new ArrayList();
    private SnakeLocation last_removed_SnakeLocation;

    // oyununu kenarlara yakın başlaması mantıksız olduğu için yılan orta kısımda olusur
    public Snake() {
        // snakeLength = 3;
        Random createRandom = new Random();

        // mapin ortalarında random x ve y değerleri 
        int x = createRandom.nextInt(Map.WIDTH - 12) + 6;
        int y = createRandom.nextInt(Map.HEIGHT - 12) + 6;

        SnakeLocation sl1 = new SnakeLocation(x, y);
        snakelocation.add(sl1);  // 0. index yani kafa

        SnakeLocation sl2 = new SnakeLocation(x + 1, y);
        snakelocation.add(sl2);

        SnakeLocation sl3 = new SnakeLocation(x + 2, y);
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
    public String checkSnakeAt(int mapX, int mapY) {
        for (int i = 0; i < snakelocation.size(); i++) {
            SnakeLocation snkloc = snakelocation.get(i);

            if (mapX == snkloc.getX() && mapY == snkloc.getY()) {
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
            w -> yukari
            a -> sol
            s -> asagi
            d -> sag
            q -> exit
         */

        int x;
        int y;

        x = snakelocation.get(0).getX();
        y = snakelocation.get(0).getY();

        if (richtungseingabe.equals("w") && y > 0) {
            SnakeLocation slW = new SnakeLocation(x, y - 1);
            snakelocation.add(0, slW);

            // yılanin elma yeme ihtimaline karsi kuyruk hafizada tutuluyor.
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        } else if (richtungseingabe.equals("w") && y == 0) {
            SnakeLocation slW = new SnakeLocation(x, Map.HEIGHT);
            snakelocation.add(0, slW);

            // yılanin elma yeme ihtimaline karsi kuyruk hafizada tutuluyor.
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        }

        if (richtungseingabe.equals("a") && x > 0) {
            SnakeLocation slA = new SnakeLocation(x - 1, y);
            snakelocation.add(0, slA);
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        } else if (richtungseingabe.equals("a") && x == 0) {
            SnakeLocation slA = new SnakeLocation(Map.WIDTH, y);
            snakelocation.add(0, slA);
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        }

        if (richtungseingabe.equals("s") && y < Map.HEIGHT - 1) {
            SnakeLocation slS = new SnakeLocation(x, y + 1);
            snakelocation.add(0, slS);
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        } else if (richtungseingabe.equals("s") && y == Map.HEIGHT - 1) {
            SnakeLocation slS = new SnakeLocation(x, 0);
            snakelocation.add(0, slS);
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        }

        if (richtungseingabe.equals("d") && x < Map.WIDTH - 1) {
            SnakeLocation slD = new SnakeLocation(x + 1, y);
            snakelocation.add(0, slD);
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        } else if (richtungseingabe.equals("d") && x == Map.WIDTH - 1) {
            SnakeLocation slD = new SnakeLocation(0, y);
            snakelocation.add(0, slD);
            last_removed_SnakeLocation = snakelocation.get(snakelocation.size() - 1);
            snakelocation.remove(snakelocation.size() - 1);
        }

        if (richtungseingabe.equals("q")) {
            System.out.println("Oyun sona erdirildi...");
            System.exit(0);
        } else if (richtungseingabe.equals("w") && y == 0) {

        }

    }
}
