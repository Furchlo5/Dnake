package com.dnake.gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
    public Image mapImage;
    public Image appleRed;
    public Image appleGold;
    public Image applePoison;
    public Image snakeHead;
    public Image snakeBody;

    public ImageLoader(){
        try {
            mapImage = ImageIO.read(new File("resources/images/map.png"));
            appleRed = ImageIO.read(new File("resources/images/apple.png"));
            appleGold = ImageIO.read(new File("resources/images/golden-apple.png"));
            applePoison = ImageIO.read(new File("resources/images/poison.png"));
            snakeHead = ImageIO.read(new File("resources/images/snake-head.png"));
            snakeBody = ImageIO.read(new File("resources/images/snake-body.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load game images", e);
        }
    }
}

