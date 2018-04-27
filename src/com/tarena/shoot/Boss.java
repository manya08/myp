package com.tarena.shoot;

public class Boss extends FlyingObject {

    public Boss() {
        this.xSpeed = rand.nextInt(2) == 0 ? -1 : 1;
        this.ySpeed = 1;
        this.width = 360;
        this.height = 300;
        this.y = -height;
        this.life = 1000 * ShootGame.LEVEL;
        this.x = rand.nextInt(ShootGame.WIDTH - width);
        this.image = Img.bosses[ShootGame.LEVEL % Img.bosses.length];
        this.ember = Img.bossEmber;
    }

    @Override
    //飞机运动
    public void step() {
        x += xSpeed;
        y += ySpeed;
        if (this.x >= ShootGame.WIDTH - this.width) {
            xSpeed = -1;
        }
        if (this.x < 0) {
            xSpeed = 1;
        }
        if (y > 30) {
            ySpeed = 0;
        }
    }
//弹幕计算
    public EBullet[] shoot() {
        int ebW = 10/2;
        int fire = (rand.nextInt(10) + 1) * 2 - 1;
        EBullet[] bs = new EBullet[fire *ShootGame.LEVEL];
        for (int i = 0; i < bs.length; i++) {
            final int xBoosSpeed = rand.nextInt(fire + 1) - fire / 2;
            final int yBossSpeed = ShootGame.LEVEL / 2 + 1;
            bs[i] = new EBullet(this.x + this.width / (bs.length + 1) * (i + 1) - ebW, this.y + this.height) {
                @Override
                public void step() {
                    x -= xBoosSpeed;
                    y += yBossSpeed;
                }
            };
        }
        return bs;
    }
}
