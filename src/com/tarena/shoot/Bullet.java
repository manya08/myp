package com.tarena.shoot;

public class Bullet extends FlyingObject {

    public Bullet(int x, int y,int life) {
        this.life = life;
        this.x = x;
        this.y = y;
        this.image = Img.bullet;
        this.ySpeed = 10;
        this.xSpeed = 1;
        this.width = 10;
        this.height = 40;
    }

    @Override
    public void step() {
        y -= ySpeed;
    }

    @Override
    public boolean outOfBounds() {
        return this.y + this.height < 0;
    }
}

