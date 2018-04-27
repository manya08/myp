package com.tarena.shoot;

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShootGame extends JPanel {
    public static final int WIDTH = 800;//场景宽
    public static final int HEIGHT = 1000;//场景高
    private Hero hero = new Hero();//我方飞机

    private ArrayList<Boss> boss = new ArrayList<Boss>();
    private ArrayList<Ember> ember = new ArrayList<Ember>();//爆炸效果
    private ArrayList<FlyingObject> flyings = new ArrayList<FlyingObject>();//飞行物
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();//我方子弹
    private ArrayList<EBullet> eBullets = new ArrayList<EBullet>();//敌方子弹

    public static int LEVEL = 1;//级别
    public static int timing = 1000;//倒计时时间
    public static int index = 0;

    private int state = 0;//当前状态
    private static final int START = 0;//初始界面
    private static final int RUNNING =1;//运行
    private static final int PAUSE = 2;//暂停
    private static final int OVER = 3;//结束



    private Paint paint = new Paint(hero, flyings, bullets, eBullets, ember,boss);
    private boolean bossLive;
    //随机产生飞机对象
    public FlyingObject nextOne() {
        Random ran = new Random();
        int type = ran.nextInt(50);
        if (type <5) {
            return new Bee();//小飞机
        } else {
            return new Airplane();//大飞机
        }
    }
    //创建飞行物到flyings中
    private void enterAction() {
        if (index % 20 == 0) {
            FlyingObject obj = nextOne();
            flyings.add(obj);
        }
    }
    //运动方法
    public void stepAction() {
        //敌方飞机移动
        for (int i = 0; i < flyings.size(); i++) {
            flyings.get(i).step();
        }//我方子弹移动
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).step();
        }//敌方子弹移动
        for (int i = 0; i < eBullets.size(); i++) {
            eBullets.get(i).step();
        }//ｂｏｓｓ移动
        for (int i = 0; i < boss.size(); i++) {
            boss.get(i).step();
        }
    }//移动的方法

    private void shootAction() {
        if (index % 10 == 0) {
            Bullet[] bs = hero.shoot();
            for (Bullet bullet : bs) {
                bullets.add(bullet);
            }

        }
    }//我方射击的方法

    private void eShootAction() {
        if (index % (150 - ShootGame.LEVEL * 5) == 0) {
            for (FlyingObject f : flyings) {
                EBullet e = new EBullet(f.x + f.width * 2 / 4, f.y);
                eBullets.add(e);
            }
        }
        if (index % (120 - ShootGame.LEVEL * 5) == 0) {
            for (Boss b : boss) {
                EBullet[] e = b.shoot();
                for (EBullet eB : e) {
                    eBullets.add(eB);
                }
            }
        }
    }//敌机射击的方法

    public void bulletShoot(FlyingObject f) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b.isHit(f)) {
                f.life -= b.life;
                bullets.remove(i);
            }
        }
    }

    public void getSAward(FlyingObject f) {
        if (f instanceof Enemy) {
            Enemy e = (Enemy) f;
            int score = hero.getScore() + e.getScore();
            hero.setScore(score);
        }
    }

    private void isDie() {
        if (hero.life <= 0) {
            ember.add(new Ember(hero));
            hero.x = ShootGame.WIDTH - hero.width >> 1;
            hero.y = (ShootGame.HEIGHT - hero.height >> 1) + 250;
            hero.setLifeNum(hero.getLifeNum() - 1);
            hero.life = 100;
            hero.setFire(1);
            for (FlyingObject f : flyings) {
                f.life = 0;
            }
        }
        for (int i = 0; i < boss.size(); i++) {
            if (boss.get(i).life <= 0) {
                ember.add(new Ember(boss.get(i)));
                boss.remove(i);
                hero.life = 100;
                hero.setLifeNum(hero.getLifeNum() + 2);
                if (hero.getFire() < 4)
                    hero.setFire(hero.getFire() + 1);
                hero.setScore(hero.getScore() + 10000 * ShootGame.LEVEL);
                hero.setDamage(hero.getDamage() + 4);
                bossLive = false;
                ShootGame.LEVEL++;
            }
        }
    }

    private void isHit() {
        // 遍历飞行物
        for (int i = 0; i < flyings.size(); i++) {
            FlyingObject f = flyings.get(i);
            bulletShoot(f);
            //判断英雄机是否碰撞
            if (hero.isHit(f)) {
                hero.life -= f.life;
                flyings.remove(i);
                ember.add(new Ember(f));
                getSAward(f);
                continue;
            }
            // 判断飞行物生命
            if (f.life <= 0) {
                flyings.remove(i);
                ember.add(new Ember(f));
                getSAward(f);
            }
        }
        // 遍历敌方子弹
        for (int j = 0; j < eBullets.size(); j++) {
            EBullet b = eBullets.get(j);
//             敌方子弹碰到我方飞机
            if (b.isHit(hero)) {
                hero.life -= b.life;
                eBullets.remove(j);
            }
        }
        // 遍历boss
        for (int i = 0; i < boss.size(); i++) {
            Boss b = boss.get(i);
            // boss碰到子弹
            bulletShoot(b);
            // boss碰到英雄机
            if (b.isHit(hero)) {
                hero.life -= b.life;
            }
        }
    }

    public void gameStatic() {
        if (hero.getLifeNum() < 0) {
            state = OVER;
        }
        switch (state) {
            case START:
                repaint();
                break;
            case RUNNING:
                isDie();
                isHit();
                if (timing == 0) {
                    boss.add(new Boss());
                    bossLive = true;
                    timing = 3000;
                }
                if (!bossLive) {
                    timing--;
                    enterAction();
                }
                index++;
                shootAction();
                eShootAction();
                stepAction();
                hero.move();
                repaint();
                break;
            case PAUSE:
                repaint();
                break;
            case OVER:
                repaint();
                break;
        }
    }

    public void rePlay() {
        index = 0;
        timing = 1000;
        ShootGame.LEVEL = 1;
        hero = new Hero();
        flyings = new ArrayList<FlyingObject>();
        bullets = new ArrayList<Bullet>();
        eBullets = new ArrayList<EBullet>();
        ember = new ArrayList<Ember>();
        paint = new Paint(hero, flyings, bullets, eBullets, ember,boss);
    }

    public Listener getListener() {
        return new Listener();
    }

    //键盘监听 KeyAdapter可以少写一些不用写全
    class Listener extends KeyAdapter {
        @Override
        //按下
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.isW = true;
                break;
            case KeyEvent.VK_DOWN:
                hero.isS = true;
                break;
            case KeyEvent.VK_LEFT:
                hero.isA = true;
                break;
            case KeyEvent.VK_RIGHT:
                hero.isD = true;
                break;
            case KeyEvent.VK_Q:
                if (state == RUNNING)
                    state = PAUSE;
                else if (state == PAUSE)
                    state = RUNNING;
                break;
            case KeyEvent.VK_ENTER:
                if (state == START || state == OVER) {
                    state = RUNNING;
                    rePlay();
                }
                break;
            }
        }

        @Override
        //弹出
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    hero.isW = false;
                    break;
                case KeyEvent.VK_DOWN:
                    hero.isS = false;
                    break;
                case KeyEvent.VK_LEFT:
                    hero.isA = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    hero.isD = false;
                    break;
            }
        }
    }

    @Override
    //判断状态
    public void paint(Graphics g) {
        if (state == START) {
            paint.paintStart(g);
            return;
        }
        if (state == PAUSE) {
            paint.paintPause(g);
            return;
        }
        if (state == OVER) {
            paint.paintOver(g);
            return;
        }
        if (state == RUNNING)
            paint.paintRunning(g);
    }



    public void action() {
        java.util.Timer timer = new Timer();
        int intervel = 10;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameStatic();
            }
        }, intervel, intervel);
    }//设定指定任务task在指定延迟delay后进行固定延迟peroid的执行
    // schedule(TimerTask task, long delay, long period)

    public static void main(String[] args) {
        JFrame frame =new JFrame("雷霆战机");
        ShootGame game = new ShootGame();
        frame.setIconImage(new ImageIcon("src/com/tarena/shoot/icon.png").getImage());
        frame.setContentPane(game);
        frame.setSize(WIDTH,HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(game.getListener());
        frame.setVisible(true);
        game.action();
    }
}
