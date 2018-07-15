package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class SpaceShip extends GameObject {

    private Bitmap spritessheet;
    private Bitmap shield;

    //    private int score;
    //    private double dya;
    public int timeShutting = 200;
    public int health = 100;
    public int timeChange = 0;
    public int scoreForStyleOne = 0;
    public int score = 0;
    private int X;
    private int Y;
    public boolean enableBullet2 = true ;
    public boolean enableBullet3 = true;
    public boolean move;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean playing;
    private Animation animation = new Animation();
    public Rect rect ;
    public Rect rectFortouch;
    //    private Animation animation = new Animation();
    private long startTime;
    private long startTime2;
    public SpaceShip(Bitmap res, Bitmap shield){
        //System.out.println(GamePanel.WIDTH);

        dy = 0;
        dx = 0;
//        score = 0;


        height = res.getHeight();
        width = res.getWidth();
        System.out.print("height: "+ height);
        System.out.print(" width: "+ width);
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT-height-200;
        spritessheet = res;
        this.shield = shield;
//        this.shield = Bitmap.createScaledBitmap(this.shield,2,3,true);

//        Bitmap[] image = new Bitmap[numFrames];
//        for(int i = 0; i < image.length; i++){
//            image[i] = Bitmap.createBitmap( spritessheet,i *width, 0, width, height);
//        }

//        animation.setDelay(10);
//        animation.setFrames(image);
        startTime = System.nanoTime();
        startTime2 = System.nanoTime();
        rect = getRectangle();
        rectFortouch = getRectangleTouch();
        if (store.SPACESHIP_STYLE == 5){
            animation.setFrames(GamePanel.NamAndYAngSpaceShip);
            animation.setDelay(10);
        }


    }

    public  void draw(Canvas canvas){
        //adsa
        
//        System.out.print(x);
//        System.out.print(y);
//        Paint fillPaint1 = new Paint();
//        fillPaint1.setColor(Color.YELLOW);
//        canvas.drawRect(rectFortouch, fillPaint1);
//        Paint fillPaint = new Paint();
//        fillPaint.setColor(Color.RED);
//        canvas.drawRect(rect, fillPaint);
        if (store.SPACESHIP_STYLE == 5){
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }
        else {
        canvas.drawBitmap(spritessheet, x,y, null);
        }
        //add shield bitmap
        canvas.drawBitmap(shield, x-20,y-25, null);
    }
    public boolean getPlaying(){return playing;}
    public Rect getRectangleTouch(){
        return new Rect(x-80,y-50, x+width*2, y+height*2);
    }


    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void moving(boolean move) {
        this.move = move;

    }
    public void setXY(int x, int y){
        X = x;
        Y = y;
    }


    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        long elapsed1 = (System.nanoTime()-startTime2)/1000000;
        if (store.SPACESHIP_STYLE ==5){
        animation.update();}
        if(elapsed>1000)
        {
            //System.out.print("timechange" + timeChange);
            timeChange++;
            startTime = System.nanoTime();
        }

        if(elapsed1>100 && !GamePanel.gameOver)
        {
            //System.out.print("timechange" + timeChange);
            scoreForStyleOne++;
            startTime2 = System.nanoTime();
        }


        if(move){
            x = X;
            y = Y;
        }
        rect = getRectangle();
        rectFortouch = getRectangleTouch();


    }

}
