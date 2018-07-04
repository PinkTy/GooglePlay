package com.example.namvu.shutting;

import android.graphics.Rect;

public abstract class GameObject {
    public int x;
    public int y;
    public int dx;
    public int dy;
    public int width;
    public int height;

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect getRectangle(){
        return new Rect(x,y, x+width, y+height);
    }

}
