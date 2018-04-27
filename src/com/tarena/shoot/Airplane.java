package com.tarena.shoot;

public class Airplane extends FlyingObject implements Enemy {

    public Airplane() {
        this.xSpeed = rand.nextInt(3) - 1;
        this.ySpeed = 3; // 移动步骤
        this.width = 48;
        this.height = 36;
        this.x = rand.nextInt(ShootGame.WIDTH - width);//飞机随即位置出现
        this.y = -height;
        this.life = 20 * ShootGame.LEVEL;
        this.image = Img.airplane;
        this.ember = Img.airplaneEmber;//飞机爆炸动画
    }

    @Override
    public int getScore() { // 获取分数
        return 50 * ShootGame.LEVEL;
    }

    @Override
    //飞机运动方法
    public void step() {
        x += xSpeed;
        y += ySpeed;
    }
}
