package com.tarena.shoot;

import java.awt.image.BufferedImage;

public class Ember {
    private int index;
    private int i;
    private int intevel = 40;
    private int x, y;
    private BufferedImage[] images;
    private BufferedImage image;

    public Ember(FlyingObject fly) {
        //哪种飞机爆炸
        this.images = fly.ember;
        this.image = images[0];
        //飞机爆炸的位置
        this.x = fly.x;
        this.y = fly.y;
    }
    //判断是否是最后一张图片
    public boolean burnDown() {
        // 运行４０次
        i++;
        if (i % intevel == 0) {
            //判断是否是最后一张图片
            if (index == images.length) {
                return true;
            }
            image = images[index++];
        }
        return false;
    }
    //返回爆炸的飞机类型确定爆炸张数
    public BufferedImage[] getImages() {
        return images;
    }
    //返回要画的爆炸图片是第几张
    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

