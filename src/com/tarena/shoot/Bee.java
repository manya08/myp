package com.tarena.shoot;

public class Bee extends FlyingObject {

    public Bee() {
        this.width = 98;
        this.height = 72;
        this.x = rand.nextInt(ShootGame.WIDTH - width);
        this.y = -height;
        this.xSpeed = rand.nextInt(3) - 1;
        this.ySpeed = 1;
        this.life = 100 * ShootGame.LEVEL;
        this.image = Img.bee;
        this.ember = Img.bigplaneEmber;
    }

    @Override
    public void step() {
        x += xSpeed;
        y += ySpeed;
        if (this.x >= ShootGame.WIDTH - this.width) {
            xSpeed = -1;
        }
        if (this.x < 0) {
            xSpeed = 1;
        }
    }
}
