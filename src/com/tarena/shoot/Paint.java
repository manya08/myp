package com.tarena.shoot;

import java.awt.*;
import java.util.ArrayList;

public class Paint {
    private int c=1;
    private int a=1;
    private Hero hero;
    private ArrayList<Boss> boss;
    private ArrayList<FlyingObject> flyings;
    private ArrayList<Bullet> bullets;
    private ArrayList<Ember> ember;
    private ArrayList<EBullet> eBullets;
    private int bg_x = 0;//背景坐标
    private int bg_y = 0;
    private int bgIndex;
    private int startIndex;
    private int overIndex;

   public  Paint(Hero hero,ArrayList<FlyingObject> flyings,ArrayList<Bullet> bullets, ArrayList<EBullet> eBullets,ArrayList<Ember> ember
             ,ArrayList<Boss> boss){
       super();
       this.flyings = flyings;
       this.hero = hero;
       this.eBullets = eBullets;
       this.bullets = bullets;
       this.ember=ember;
       this.boss = boss;
   }
//画初始界面
    public void paintStart(Graphics g) {

        g.drawImage(Img.start, bg_x, 0, 1800, ShootGame.HEIGHT, null);
        startIndex++;
        if (startIndex % 2 == 0) {
            if (bg_x <= 0 && bg_x > ShootGame.WIDTH - 1800 && c% 1000 != 0) {
                bg_x -=a;
                c++;
                if (c%1000==0){
                    c++;
                    a=-a;
                    System.out.println(a);
                }
            }
        }
    }

    private void paintBackGround(Graphics g) {
        g.drawImage(Img.bgs[ShootGame.LEVEL % Img.bgs.length], 0, bg_y, ShootGame.WIDTH, ShootGame.HEIGHT, null);
        g.drawImage(Img.bgs[ShootGame.LEVEL % Img.bgs.length], 0, bg_y - ShootGame.HEIGHT, ShootGame.WIDTH,
                ShootGame.HEIGHT, null);
        bgIndex++;
        if (bgIndex % 2 == 0) {
            bg_y += 1;
            if (bg_y >= ShootGame.HEIGHT) {
                bg_y = 0;
            }
        }
    }

    private void paintScore(Graphics g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("黑体", Font.BOLD, 15));
        g.drawString("游戏分数:" + hero.getScore(), 12, 80);
        g.drawString("游戏等级:" + ShootGame.LEVEL, ShootGame.WIDTH - 120, 20);
        g.drawString("游戏时间:" + ShootGame.index / 100, ShootGame.WIDTH - 120, 40);
        g.drawString("子弹伤害:" + hero.getDamage(), ShootGame.WIDTH - 120, 60);
        g.drawString("BOSS倒计时:" + ShootGame.timing / 100, ShootGame.WIDTH - 120, 80);
    }

    private void paintLifeNum(Graphics g) {
        for (int i = 0; i < hero.getLifeNum(); i++) {
            g.drawImage(Img.lifeNum, 12 * ((i << 1) + 1), 34, 16, 16, null);
        }
    }

    private void paintBossLife(Graphics g) {
        for (Boss b : boss) {
            g.setColor(Color.BLACK);
            g.fillRect(b.x, b.y - 20, b.width, 20);
            g.setColor(Color.WHITE);
            g.fillRect(b.x + 1, b.y - 19, b.width - 2, 18);
            g.setColor(Color.BLACK);
            g.fillRect(b.x + 2, b.y - 18, b.width - 4, 16);
            int w = (int) (b.life / (1000.0 * ShootGame.LEVEL) * (b.width - 4));
            // 求出颜色
            int subIdx = (int) (b.life / (1000.0 * ShootGame.LEVEL) * Img.rect.getWidth()) - 10;
            // 绘制值槽
            g.drawImage(Img.rect, b.x + 2, b.y - 18, b.x + 2 + w, b.y - 2, subIdx, 0, subIdx + 1, Img.rect.getHeight(),
                    null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("黑体", Font.BOLD, 15));
            g.drawString("生命值", b.x + 2, b.y - 18);
        }
    }

    private void paintLife(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(10, 10, 150, 20);
        g.setColor(Color.WHITE);
        g.fillRect(11, 11, 148, 18);
        g.setColor(Color.BLACK);
        g.fillRect(12, 12, 146, 16);
        int w = (int) (hero.life / 100.0 * 146) + 12;
        // 求出颜色
        int subIdx = (int) (hero.life / 100.0 * Img.rect.getWidth()) - 20;
        // 绘制值槽
        g.drawImage(Img.rect, 12, 12, w, 28, subIdx, 0, subIdx + 1, Img.rect.getHeight(), null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.BOLD, 15));
        g.drawString("生命值", 12, 26);
    }

    private void paintHero(Graphics g) {
        g.drawImage(hero.image, hero.x, hero.y, hero.width, hero.height, null);
    }

    private void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.size(); i++) {
            FlyingObject f = flyings.get(i);
            if (f.outOfBounds()) {
                flyings.remove(i);
                continue;
            }
            g.drawImage(f.image, f.x, f.y, f.width, f.height, null);
        }
    }

    public void paintBoss(Graphics g) {
        for (int i = 0; i < boss.size(); i++)
            g.drawImage(boss.get(i).image, boss.get(i).x, boss.get(i).y, boss.get(i).width, boss.get(i).height, null);
    }

    private void paintBullet(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b.outOfBounds()) {
                bullets.remove(i);
                continue;
            }
            g.drawImage(b.image, b.x, b.y, b.width, b.height, null);
        }
    }

    private void paintEBullet(Graphics g) {
        for (int i = 0; i < eBullets.size(); i++) {
            EBullet b = eBullets.get(i);
            if (b.outOfBounds()) {
                eBullets.remove(i);
                continue;
            }
            g.drawImage(b.image, b.x, b.y, b.width, b.height, null);
        }
    }
//画爆炸　画爆炸是一直运行的
    private void paintEmber(Graphics g) {
        //判断ember内有没有对象　如果有开始绘画　
        for (int i = 0; i < ember.size(); i++) {
            //第几个爆炸的飞机
            Ember e = ember.get(i);
            //判断爆炸的飞机的图片数量
            for (int j = 0; j < e.getImages().length; j++) {
                if (!e.burnDown()) {
                    g.drawImage(e.getImage(), e.getX(), e.getY(), null);
                }
            }
            if (e.burnDown()) {
//                飞机爆炸全部画完了　把飞机从ember中移出
                ember.remove(i);
            }
        }
    }

    public void paintPause(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("黑体", Font.BOLD, 30));
        g.drawString("暂停中...", ShootGame.WIDTH / 2 -80, ShootGame.HEIGHT / 2);
    }

    public void paintOver(Graphics g) {
        g.drawImage(Img.over, bg_x, 0, ShootGame.WIDTH, ShootGame.HEIGHT, null);
        g.drawImage(Img.over, bg_x + ShootGame.WIDTH, 0, ShootGame.WIDTH, ShootGame.HEIGHT, null);
        overIndex++;
        if (overIndex % 2 == 0) {
            // 轮训
            bg_x -= 1;
            if (bg_x <= -ShootGame.WIDTH) {
                bg_x = 0;
            }
        }
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("黑体", Font.BOLD, 20));
        g.drawString("本次游戏得分:" + hero.getScore(), ShootGame.WIDTH / 2 - 100, ShootGame.HEIGHT / 2);
    }

    public void paintRunning(Graphics g) {
        paintBackGround(g);
        paintHero(g);
        paintFlyingObjects(g);
        paintBullet(g);
        paintEBullet(g);
        paintLife(g);
        paintScore(g);
        paintLifeNum(g);
        paintEmber(g);
        paintBoss(g);
        paintBossLife(g);
    }
}