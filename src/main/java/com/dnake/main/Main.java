// TODO: yeni bir package keyword'u kullanıldığında path doğru olmasına rağmen IDE kırmızı yakıyor.

package com.dnake.main;

import com.dnake.engine.GameState;
import com.dnake.gui.GamePanel;
import com.dnake.gui.ImageLoader;
import com.dnake.threads.*;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        // --- 1. PAYLAŞILAN KAYNAKLARI (SHARED RESOURCES) YARAT ---
        // Hem klavye dinleyicisinin hem de oyun döngüsünün kullanacağı ortak mesajlaşma kuyruğu
        InputQueue inputQueue = new InputQueue();
        
        // Oyunun anlık durumunu ve resimlerini tutan nesneler
        ImageLoader images = new ImageLoader();
        GameState state = new GameState();
        
        // (Opsiyonel) Terminal versiyonunu şimdilik devre dışı bırakabilir veya silmeyebilirsin
        // Map gameMap = new Map();
        // gameMap.startGame();
        

        // --- 2. ARAYÜZÜ (GUI) YARAT VE BAĞLANTILARI KUR ---
        // GamePanel'e ihtiyacı olan tüm parçaları (resimler, durum, kuyruk) veriyoruz
        GamePanel panel = new GamePanel(images, state, inputQueue);
        
        JFrame frame = new JFrame("Dnake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Pencereyi ekranın tam ortasında açar
        frame.setVisible(true);

        // --- 3. İLERİDE BURADA THREAD'LERİ BAŞLATACAĞIZ ---
        GameLoopThread loopThread = new GameLoopThread(state, inputQueue, panel);
        loopThread.start();
    }
}