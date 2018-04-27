package com.tarena.shoot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Img {
    public static BufferedImage start;
    public static BufferedImage over;
    public static BufferedImage bee;
    public static BufferedImage airplane;
    public static BufferedImage pause;
    public static BufferedImage bullet;
    public static BufferedImage ebullet;
    public static BufferedImage[] heros = new BufferedImage[6];
    public static BufferedImage[] bgs = new BufferedImage[4];
    public static BufferedImage[] airplaneEmber = new BufferedImage[6];
    public static BufferedImage[] bigplaneEmber = new BufferedImage[6];
    public static BufferedImage[] heroEmber = new BufferedImage[6];
    public static BufferedImage[] bossEmber = new BufferedImage[6];
    public static BufferedImage[] bosses = new BufferedImage[9];
    public static BufferedImage background;
    public static BufferedImage rect;
    public static BufferedImage lifeNum;
    static {
        try{
            start = getBufferedImage("bg_start_new.png");
            lifeNum = getBufferedImage("lifeNum.png");
            rect = getBufferedImage("rect.png");
            background = getBufferedImage("bg_2.jpg");
            airplane = getBufferedImage("airplane.png");
            bee = getBufferedImage("bee.png");
            ebullet = getBufferedImage("Bomb1.png");
            over = getBufferedImage("bg_over.png");
            pause = getBufferedImage("pause.png");
            bullet = getBufferedImage("Bomb3_enemy.png");
            for (int i = 0; i < 6; i++) {
                airplaneEmber[i] = getBufferedImage("blast_1_" + (i + 1) + ".png");
                bigplaneEmber[i] = getBufferedImage("blast_3_" + (i + 1) + ".png");
                heroEmber[i] = getBufferedImage("blast_2_" + (i + 1) + ".png");
                heros[i] = getBufferedImage(i + ".png");
                bossEmber[i] = getBufferedImage("bossEmber" + i + ".png");
            }
            for (int i = 0; i < bgs.length; i++) {
                bgs[i] = getBufferedImage("bg_" + i + ".jpg");
            }
            for (int i = 0; i < bosses.length; i++) {
                bosses[i] = getBufferedImage("boss_" + i + ".png");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static BufferedImage getBufferedImage(String imageName){
        try{
            return ImageIO.read(Img.class.getResource(imageName));
        }catch (Exception e){
            System.out.println(imageName + "读取失败！");
            e.getStackTrace();
        }
        return null;
    }
}
