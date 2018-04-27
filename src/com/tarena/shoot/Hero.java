package com.tarena.shoot;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
    private BufferedImage[] images;
    private int fire;
    private int score;
    private int lifeNum;
    private int damage;
    boolean isW, isS, isA, isD;

    public Hero() {
        this.xSpeed = 5;
        this.ySpeed = 5;//移动速度
        this.life = 100;//生命初始值
        this.fire = 2;//2束子弹
        this.images = Img.heros;
        this.image = images[0];
        this.width = 80;
        this.height = 80;
        this.damage=5;//伤害初始值
        this.score = 0;//分数初始值
        this.lifeNum = 6;//生命条数
        this.x=(ShootGame.WIDTH-width)/2;//起始坐标
        this.y = (ShootGame.HEIGHT-height)/8*7;
        this.ember = Img.heroEmber;	     //爆炸图片
    }

    public void step() {

    }

    //子弹射击
    public Bullet[] shoot() {
        int ebW = 5;
        //
        Bullet[] bs = new Bullet[fire];
        if (fire % 2 != 0) {
            for (int i = 0; i < bs.length; i++) {
                int xs = i - bs.length / 2;
                bs[i] = new Bullet(this.x+ this.width / (bs.length + 1) * (i + 1) - ebW, this.y+2, damage) {
                    @Override
                    public void step() {
                        x += xs;
                        y -= ySpeed;
                    }
                };
            }
        } else {
            for (int i = 0; i < bs.length; i++) {
                 int xs = (i - bs.length / 2)< 0 ? (i - bs.length / 2) : (i - bs.length / 2) + 1;//保持子弹对称
                 //
                bs[i] = new Bullet(this.x + this.width / (bs.length + 1) * (i + 1) - ebW, this.y+2, damage) {
                    @Override
                    public void step() {
                        x += xs;
                        y -= ySpeed;
                    }
                };
            }
        }
        return bs;
    }

    public void move() {
        if (isW && y > 0)
            this.y -= ySpeed;
        if (isS && y + this.height + 30 < ShootGame.HEIGHT)
            this.y += ySpeed;
        if (isA && x > 0)
            this.x -= xSpeed;
        if (isD && x + this.width < ShootGame.WIDTH)
            this.x += xSpeed;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) { this.score = score; }
    public int getFire() {
        return fire;
    }
    public void setFire(int fire) {
        this.fire = fire;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getLifeNum() {
        return lifeNum;
    }
    public void setLifeNum(int lifeNum) {
        this.lifeNum = lifeNum;
    }
}
