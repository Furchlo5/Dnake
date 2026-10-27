// TODO: yeni bir package keyword'u kullanıldığında path doğru olmasına rağmen IDE kırmızı yakıyor.

package com.dnake.main;

import com.dnake.engine.GameState;
import com.dnake.gui.*;
import com.dnake.threads.*;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


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
        GamePanel gamePanel = new GamePanel(images, state, inputQueue);
        
        // eski version 
        // JFrame frame = new JFrame("Dnake");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.add(panel);
        // frame.pack();
        // frame.setLocationRelativeTo(null); // Pencereyi ekranın tam ortasında açar
        // frame.setVisible(true);

        // artık CardLayout kullanan yeni bir MainContainer ekleyeceğiz
        // --- YENİ EKLENEN KISIM: CARD LAYOUT (EKRAN YÖNETİCİSİ) ---
        CardLayout cardLayout = new CardLayout();
        JPanel mainContainer = new JPanel(cardLayout); // Tüm ekranları tutacak ana sepet

        // Menü panelini yaratıyoruz ve ona geçiş yapabilmesi için gereken aletleri veriyoruz
        MenuPanel menuPanel = new MenuPanel(mainContainer, cardLayout, gamePanel);

        // Ekranları kimlik isimleriyle (MENU, GAME) CardLayout sepetine ekliyoruz
        mainContainer.add(menuPanel, "MENU");
        mainContainer.add(gamePanel, "GAME");
        // ----------------------------------------------------------

        JFrame frame = new JFrame("Dnake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Eskiden gamePanel ekliyorduk, artık sepeti (mainContainer) ekliyoruz
        frame.add(mainContainer);
        frame.pack();
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        // Program açıldığında ilk olarak MENU ekranını göster!
        cardLayout.show(mainContainer, "MENU");

        // --- 3. İLERİDE BURADA THREAD'LERİ BAŞLATACAĞIZ ---
        GameLoopThread loopThread = new GameLoopThread(state, inputQueue, gamePanel);
        loopThread.start();
    }
}