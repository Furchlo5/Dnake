package com.dnake.gui;

import com.dnake.engine.GameState;
import com.dnake.threads.InputQueue; // Kuyruğumuzu import ediyoruz
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GamePanel extends JPanel{
    private final int cellSize = 32;
    private final ImageLoader images;
    private final GameState state;
    private final InputQueue inputQueue; // Klavye girdilerini atacağımız kuyruk nesnesi

    public GamePanel(ImageLoader images, GameState state, InputQueue inputQueue){
        this.images = images;
        this.state = state;
        this.inputQueue = inputQueue;
        setPreferredSize(new Dimension(1024, 1024));

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
        g.drawImage(images.mapImage, 0, 0, 1024, 1024, null);
    

        // 2. Elmayı çiz (Eğer varsa)
        if (state.getFood() != null) {
            int foodX = state.getFood().getX() * cellSize;
            int foodY = state.getFood().getY() * cellSize;
            g.drawImage(images.appleRed, foodX, foodY, cellSize, cellSize, null);
        }

        // 3. Yılanı çiz
        java.util.ArrayList<com.dnake.entities.SnakeLocation> body = state.getSnake().getSnakeLocation();
        for (int i = 0; i < body.size(); i++) {
            int partX = body.get(i).getX() * cellSize;
            int partY = body.get(i).getY() * cellSize;

            if (i == 0) {
                // 0. index yılanın kafasıdır
                g.drawImage(images.snakeHead, partX, partY, cellSize, cellSize, null);
            } else {
                // Diğerleri yılanın gövdesidir
                g.drawImage(images.snakeBody, partX, partY, cellSize, cellSize, null);
            }
        }
    }
}
