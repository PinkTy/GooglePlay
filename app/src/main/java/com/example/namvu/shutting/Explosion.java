package com.example.namvu.shutting;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {
    private int x;
    private int y;
    private int width;
    public boolean remove;
    private int height;
    private int row;
    private Animation animation = new Animation();
    private Bitmap[] images;
    public  Explosion(Bitmap[] res, int x, int y){
        this.x= x;
        this.y = y;
        images = res;
        animation.setFrames(images);
        animation.setDelay(10);
    }
    public void  draw(Canvas canvas){
        if(!animation.PlayedOnce())
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }

    public void  update(){
        if(!animation.PlayedOnce()){
            animation.update();
        }
        else {
            remove = true;
        }
    }

    public int getHeight() {
        return height;
    }
}