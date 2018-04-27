package com.tarena.shoot;

public class EBullet extends FlyingObject {

    public EBullet(int x, int y) {
        this.life = 5;
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 15;
        this.ySpeed = 4;
        this.image = Img.ebullet;
    }

    @Override
    public void step() {
        y += ySpeed;
    }

    @Override
    public boolean outOfBounds() {
        return this.y > ShootGame.HEIGHT;
    }

}
