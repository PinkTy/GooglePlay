package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.Random;

public class Rock extends GameObject {
    private Random rand = new Random();
    private Bitmap spritessheet;
    Matrix matrix = new Matrix();
    public long lastupdate;
    public long timenow;
    public int rot, rotspeed;

    public Rect rectangle;
    private  int score =30;
    int speedX;
    int speedY;
    public Rock(Bitmap res){
        spritessheet = res;
        this.width = spritessheet.getWidth();
        this.height = spritessheet.getHeight();
        this.x = 0 + rand.nextInt(GamePanel.WIDTH + 1);
        this.y = -300 + rand.nextInt(100);
        rectangle = getRectangle();
        speedX = -5 + rand.nextInt(15 - 5 + 1);
        speedY =  7 + rand.nextInt(30 + 1);
        if(speedY > 100){
            speedY = 100;
        }
        lastupdate = System.nanoTime();
        rot = 0;
        rotspeed = -8 + rand.nextInt(17);

    }
    // try to rotate the rock it was work but it so lagging
//    public void rotate(){
//        timenow = System.nanoTime();
//
//
//        if(timenow - lastupdate > 50){
//            lastupdate = timenow;
//
//            Bitmap newbitmap;
//            rot = (rotspeed + rot) %360;
    //          RotateBitmap
            // find on
//            newbitmap = RotateBitmap(spritessheet, rot);
//            //float oldcenter = rectangle.exactCenterX();
//            spritessheet = newbitmap;
//
//
//
//        }
//    }

// update and reset the rock if it out of screen
    public void update(){
        x += speedX;
        y += speedY;
        if(y > GamePanel.HEIGHT + 200 || rectangle.left < - 200 || rectangle.left > GamePanel.WIDTH + 25){
            this.x = 0 + rand.nextInt(GamePanel.WIDTH + 1);
            this.y = -150 + rand.nextInt(100);
            speedX = -5 + rand.nextInt(15 - 5 + 1);
            speedY =  7 + rand.nextInt(30 + 1);
        }
//        rotate();
        rectangle = getRectangle();

    }
    public void draw(Canvas canvas){
        try{
//            Paint fillPaint = new Paint();
//            fillPaint.setColor(Color.RED);
//            canvas.drawRect(rectangle, fillPaint);
            canvas.drawBitmap(spritessheet, x, y, null);
        }catch (Exception e){

        }

    }

}
