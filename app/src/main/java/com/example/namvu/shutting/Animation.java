package com.example.namvu.shutting;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] frames;
    private int currentFrames;
    private long strartTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrames = 0;
        strartTime = System.nanoTime();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    public void setCurrentFrames(int i){
        currentFrames=i;
    }
    public void update(){
        long elapsed = (System.nanoTime()-strartTime)/10000000;
        if(elapsed > delay){
            currentFrames++;
            strartTime = System.nanoTime();
        }
        if(currentFrames ==frames.length){
            currentFrames =0;
            playedOnce = true;
        }
    }
    public Bitmap getImage(){
        return  frames[currentFrames];

    }
    public  int getFreme(){
        return currentFrames;
    }
    public boolean PlayedOnce(){
        return playedOnce;
    }
}
