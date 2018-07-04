package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.Animation;

public class SpaceShip extends GameObject {
    private Bitmap spritessheet;
    //    private int score;
    //    private double dya;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean playing;
    public Rect rect ;
    //    private Animation animation = new Animation();
    private long startTime;
    public SpaceShip(Bitmap res){
        //System.out.println(GamePanel.WIDTH);

        dy = 0;
        dx = 0;
//        score = 0;

        spritessheet = res;
        height = res.getHeight();
        width = res.getWidth();
        System.out.print("height: "+ height);
        System.out.print(" width: "+ width);
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT-height;
//        Bitmap[] image = new Bitmap[numFrames];
//        for(int i = 0; i < image.length; i++){
//            image[i] = Bitmap.createBitmap( spritessheet,i *width, 0, width, height);
//        }

//        animation.setDelay(10);
//        animation.setFrames(image);
        startTime = System.nanoTime();
        rect = getRectangle();


    }
    public  void draw(Canvas canvas){
//        System.out.print(x);
//        System.out.print(y);
        Paint fillPaint = new Paint();
        fillPaint.setColor(Color.RED);
        canvas.drawRect(rect, fillPaint);

        canvas.drawBitmap(spritessheet, x,y, null);
    }
    public boolean getPlaying(){return playing;}
    public int getCentralImageX() {
//        System.out.println(x);
////        System.out.println(width);
        return x + width /2;
    }
    public int getCentralImageY() {
        return y - height /2;
    }
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void update(){

        if(up){
            dy-=20;

        }
        if(down){
            dy+=20;
        }
        if(right){
            dx+=20;

        }
        if(left){
            dx-=20;
        }
        x += dx;
        y += dy;
        dy = 0; dx = 0;
        rect = getRectangle();

    }

}
