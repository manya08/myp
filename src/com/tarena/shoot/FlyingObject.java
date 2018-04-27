package com.tarena.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class FlyingObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected BufferedImage image;
    protected int life;
    protected int xSpeed;
    protected int ySpeed;
    protected BufferedImage[] ember;
    Random rand = new Random();
    public abstract void step();

    public boolean outOfBounds(){
        return y>=ShootGame.HEIGHT;
    }
    //判断是否和飞机相撞
    public boolean isHit(FlyingObject f){
        if(f.x+f.width>=this.x&&f.x<=this.x+this.width&&f.y+
                f.height>=this.y&&f.y<=this.y+this.height){
            return true;
        }
        return false;
    }
}
