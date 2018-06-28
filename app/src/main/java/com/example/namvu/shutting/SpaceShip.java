package com.example.namvu.shutting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
//    private Animation animation = new Animation();
    private long startTime;
    public SpaceShip(Bitmap res, int w, int h, int numFrames){
        //System.out.println(GamePanel.WIDTH);
        x = GamePanel.WIDTH/2-w/2;
        y = GamePanel.HEIGHT-2*h ;
        dy = 0;
        dx = 0;
//        score = 0;
        height = h;
        width = w;
        spritessheet = res;

//        Bitmap[] image = new Bitmap[numFrames];
//        for(int i = 0; i < image.length; i++){
//            image[i] = Bitmap.createBitmap( spritessheet,i *width, 0, width, height);
//        }

//        animation.setDelay(10);
//        animation.setFrames(image);
        startTime = System.nanoTime();

    }
    public  void draw(Canvas canvas){
//        System.out.print(x);
//        System.out.print(y);
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
            y-=10;

        }
        if(down){
            y+=10;
        }
        if(right){
            x+=10;

        }
        if(left){
            x-=10;
        }

    }

}
