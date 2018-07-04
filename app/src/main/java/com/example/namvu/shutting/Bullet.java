package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public class Bullet extends GameObject {
    private Bitmap spritessheet;
    public Rect rectangle;
    int speedX;
    int speedY;
    public Bullet(Bitmap res, int x, int y, int speedy, int speedx){
        spritessheet = res;
        this.width = spritessheet.getWidth();
        this.height = spritessheet.getHeight();
        this.x = x;
        this.y = y;
        rectangle = getRectangle();
        speedX = speedx;
        speedY = speedy;


    }
    public void update(){
        x -= speedX;
        y += speedY;
        //rectangle = getRectangle();

    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(spritessheet, x, y, null);
        }catch (Exception e){

        }

    }
}
