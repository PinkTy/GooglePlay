package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class BaseWindow extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    public int currentFrame;
    public float scalex;
    public float scaley;
    public float screen_width;
    public float screen_height;
    public boolean threadFlag;
    public Paint paint;
    public Canvas canvas;
    public Thread thread;
    public SurfaceHolder sfh;
    public GameSoundPool sounds;
    public Game game;


    public BaseWindow(Context context, GameSoundPool sounds) {
        super(context);
        this.sounds = sounds;
        this.game = (Game) context;
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        screen_width = this.getWidth();
        screen_height = this.getHeight();
        threadFlag = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        threadFlag = false;
    }

    public void initBitmap() {
    }

    public void release() {
    }

    public void drawSelf() {
    }

    @Override
    public void run() {
        }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }



}
