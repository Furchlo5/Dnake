package com.dnake.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputQueue {
    // Thread-safe (İplik korumalı) bir kuyruk oluşturuyoruz.
    // Kapasitesini 5 yaptık, yani oyuncu çok hızlı 5 tuşa bassa bile kaybolmayacak, sıraya girecek.
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>(1);

    // Dinleyici Thread (InputListenerThread) klavyeden harf okudukça buraya ekleyecek
    public void addInput(String input) {
        // Eğer kuyruk doluysa (oyuncu çok hızlı bastıysa) eskiyi sil, yeniyi ekle

        // Neden add() yerine offer() kullanıyoruz? Bizim kuyruğumuzun limitini 5 olarak belirlemiştik. Eğer kuyruğun kapasitesi dolsaydı ve biz standart add() metodunu kullansaydık, program anında çöküp Exception (Hata) fırlatırdı.
        // offer() asla programı çökertmez. Eğer kuyrukta yer varsa elemanı ekler ve true döner. Yer yoksa sessizce false döner. Böylece oyuncu heyecanlanıp klavyeye saniyede 20 kere bassa bile oyunumuz "Kapasite doldu!" diyerek çökmez, güvenle çalışmaya devam eder.

        // Neden take() veya remove() kullanmadık? take() metodu, kuyruk boşsa içine yeni bir eleman gelene kadar thread'i sonsuza kadar dondurur (blocklar). Eğer bunu Game Loop içinde yapsaydık, sen yeni bir tuşa basana kadar yılan olduğu yerde donup kalırdı! remove() ise kuyruk boşsa hata fırlatıp oyunu yine çökertir.
        // poll() tamamen "Non-blocking" (engelleyici olmayan) ve çok güvenli bir metottur. Kuyrukta okunacak harf varsa onu verir ve kuyruktan atar. Eğer sen o an hiçbir tuşa basmamışsan (yani kuyruk tamamen boşsa), programı dondurmaz veya çökertmez; sadece null (boşluk) döner. Oyun döngümüz de null geldiğini görünce "Demek ki oyuncu tuşa basmamış, ben yılanı mevcut yönünde yürütmeye devam edeyim" der ve akış kesilmez.

        if (!queue.offer(input)) { 
            queue.poll(); // En eskisini at
            queue.offer(input); // kuyruğun en arkasına yeniyi ekle
        }
    }

    // Oyun Döngüsü Thread'i (GameLoopThread) her adımda sıradaki tuşu buradan çekecek
    public String getNextInput() {
        return queue.poll(); // Eğer kuyruk boşsa null döner, bekleme yapmaz (Non-blocking)
    }
}
