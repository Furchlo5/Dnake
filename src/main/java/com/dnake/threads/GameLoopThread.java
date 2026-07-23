package com.dnake.threads;

import com.dnake.engine.GameState;
import com.dnake.gui.GamePanel;

// Thread sınıfından miras alarak arka plan işçisi yapıyoruz
public class GameLoopThread extends Thread {
    private final GameState state;
    private final InputQueue inputQueue;
    private final GamePanel panel;

    // Constructor: Şef (Main) bu işçiye çalışması için gereken aletleri veriyor
    public GameLoopThread(GameState state, InputQueue inputQueue, GamePanel panel) {
        this.state = state;
        this.inputQueue = inputQueue;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            // 1. GİRDİ KONTROLÜ: Kuyrukta bekleyen harf var mı? (Non-blocking / dondurmayan metot)
            String input = inputQueue.getNextInput();
            
            if (input != null) {
                // Şimdilik sistemin çalıştığını görmek için terminale yazdıralım. 
                // Bir sonraki adımda bunu GameState içindeki yılanın yönüne bağlayacağız!
                System.out.println("Key Listener: " + input);
                
                // Eğer oyuncu P'ye bastıysa, durumu değiştirme mantığı buraya gelecek
            }

            // 2. MANTIK GÜNCELLEMESİ (Logic Update)
             state.update(input); // TODO: Yılanı yürütme ve çarpışma testleri buraya eklenecek

            // 3. EKRANI YENİLE (Render)
            // Bu metot Swing'in Event Dispatch Thread'ine (EDT) "müsait olduğunda ekranı çiz" mesajı yollar.
            panel.repaint();

            // 4. OYUN HIZI (FPS / Frame Rate Kontrolü)
            try {
                // Yılanın hızını buradan ayarlıyoruz. 200ms = Saniyede 5 kare (5 FPS)
                Thread.sleep(200); 
            } catch (InterruptedException e) {
                e.printStackTrace();
                break; // Thread hata alırsa döngüyü kır ve çık
            }
        }
    }
}