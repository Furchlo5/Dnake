package com.dnake.gui;

import com.dnake.engine.GameState;
import com.dnake.entities.*; 
import com.dnake.threads.InputQueue;        // Kuyruğumuzu import ediyoruz
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;


public class GamePanel extends JPanel{
    private final int cellSize = 32;
    private final ImageLoader images;
    private final GameState state;
    private final InputQueue inputQueue; // Klavye girdilerini atacağımız kuyruk nesnesi

    public GamePanel(ImageLoader images, GameState state, InputQueue inputQueue){
        this.images = images;
        this.state = state;
        this.inputQueue = inputQueue;
        setPreferredSize(new Dimension(960, 960));

        // 1. KRİTİK AYAR: Panelin klavye girdilerini dinleyebilmesi için odaklanabilir yapıyoruz
        setFocusable(true); 
        requestFocusInWindow();

        // 2. KRİTİK AYAR: Panele bir KeyListener (Klavye Dinleyicisi) ekliyoruz
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Oyuncu bir tuşa bastığında tuşun kodunu alıyoruz
                int key = e.getKeyCode();

                // Basılan tuşa göre daha önce yazdığımız o güvenli kuyruğa (Queue) mesaj atıyoruz!
                // Yön tuşlarını (Ok tuşları) da W, A, S, D'nin yanına alternatif olarak ekliyoruz.
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                    inputQueue.addInput("w");
                } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                    inputQueue.addInput("s");
                } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                    inputQueue.addInput("a");
                } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                    inputQueue.addInput("d");
                } else if (key == KeyEvent.VK_P) {
                    inputQueue.addInput("p");
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(images.mapImage, 0, 0, 960, 960, null);
    

        // Elmayı çiz (Eğer varsa)
        if (state.getFood() != null) {
            int foodX = state.getFood().getX() * cellSize;
            int foodY = state.getFood().getY() * cellSize;
            g.drawImage(images.appleRed, foodX, foodY, cellSize, cellSize, null);
        }

        // poison çiz (varsa)
        if (state.getPoison() != null){
            int poisonX = state.getPoison().getX() * cellSize;
            int poisonY = state.getPoison().getY() * cellSize;
            g.drawImage(images.applePoison, poisonX, poisonY, cellSize, cellSize, null);

        }

        // 3. Yılanı çiz
        ArrayList<SnakeLocation> body = state.getSnake().getSnakeLocation();

        // Önce Gövdeyi Çiz (Döngüyü 1'den başlatıyoruz ki kafayı atlasın)
        for (int i = 1; i < body.size(); i++) {
            int partX = body.get(i).getX() * cellSize;
            int partY = body.get(i).getY() * cellSize;

            g.drawImage(images.snakeBody, partX, partY, cellSize, cellSize, null);
        }

        // En Son Kafayı Çiz (Böylece kafa daima gövdenin üstünde, yani görünür kalır!)
        int headX = body.get(0).getX() * cellSize;
        int headY = body.get(0).getY() * cellSize;
        g.drawImage(images.snakeHead, headX, headY, cellSize, cellSize, null);

        // draw score on top of the bar
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Monospaced", Font.BOLD, 28));
        String scoreText = "SCORE: " + state.getScore();
        g2.drawString(scoreText, 20, 42);

        // OYUN BİTTİYSE EKRANIN ORTASINA GAME OVER YAZ!
        if (state.isGameOver()) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Monospaced", Font.BOLD, 80)); // Kocaman font
            g2.drawString("GAME OVER", 250, 480); // Şimdilik göz kararı ortaladık
        }
    }
}
