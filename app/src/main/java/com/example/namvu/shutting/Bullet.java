package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public class Bullet extends GameObject {
    private Bitmap spritessheet;
    public Rect rectangle;
    public Bullet(Bitmap res, int x, int y, int w, int h){
        spritessheet = res;
        this.width = w;
        this.height = h;
        rectangle = getRectangle();
        rectangle.bottom = y;


    }
}
