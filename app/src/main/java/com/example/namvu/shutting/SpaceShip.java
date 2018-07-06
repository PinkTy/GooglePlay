package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.Animation;


public class SpaceShip extends GameObject {

    private Bitmap spritessheet;
    private Bitmap shield;
    //    private int score;
    //    private double dya;
    private int X;
    private int Y;
    private boolean move;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean playing;
    public Rect rect ;
    public Rect rectFortouch;
    //    private Animation animation = new Animation();
    private long startTime;
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
        y = GamePanel.HEIGHT-height;
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
        rect = getRectangle();
        rectFortouch = getRectangleTouch();


    }

    public  void draw(Canvas canvas){
//        System.out.print(x);
//        System.out.print(y);
//        Paint fillPaint1 = new Paint();
//        fillPaint1.setColor(Color.YELLOW);
//        canvas.drawRect(rectFortouch, fillPaint1);
//        Paint fillPaint = new Paint();
//        fillPaint.setColor(Color.RED);
//        canvas.drawRect(rect, fillPaint);
        canvas.drawBitmap(spritessheet, x,y, null);
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

        if(move){
            x = X;
            y = Y;
        }
        rect = getRectangle();
        rectFortouch = getRectangleTouch();


    }

}
