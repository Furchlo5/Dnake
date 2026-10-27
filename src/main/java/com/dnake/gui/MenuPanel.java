package com.dnake.gui;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuPanel extends JPanel {
    // Main'den geçiş araçlarını ve Oyun panelini alıyoruz
    public MenuPanel(JPanel mainContainer, CardLayout cardLayout, GamePanel gamePanel) {
        
        // 1. DIŞ DÜZEN: Her şeyi ekranın tam ortasına hizalamak için GridBagLayout kullanıyoruz
        setLayout(new GridBagLayout());

        // 2. İÇ DÜZEN: Butonları 3 satır, 1 sütun (alt alta) dizecek ve aralarına 20px boşluk koyacak kutu
        JPanel buttonBox = new JPanel(new GridLayout(3, 1, 20, 20));
        
        // Butonları yaratıyoruz
        JButton playButton = new JButton("PLAY");
        JButton scoreButton = new JButton("SCORE");
        JButton settingsButton = new JButton("SETTINGS");

        // Butonların fontunu ve boyutunu biraz havalı yapalım
        Font buttonFont = new Font("Monospaced", Font.BOLD, 36);
        playButton.setFont(buttonFont);
        scoreButton.setFont(buttonFont);
        settingsButton.setFont(buttonFont);

        // Butonları kutuya ekliyoruz
        buttonBox.add(playButton);
        buttonBox.add(scoreButton);
        buttonBox.add(settingsButton);

        // Kutuyu ana menü paneline ekliyoruz
        add(buttonBox);

        // 3. TIKLANMA OLAYI (Action Listener)
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContainer, "GAME");
                gamePanel.requestFocusInWindow();
            }
        });
    }

}