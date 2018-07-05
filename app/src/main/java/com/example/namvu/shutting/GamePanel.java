package com.example.namvu.shutting;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public Rect rect;
    public static int copySavestate;
    public float copyscaleFactorX;
    public float copyscaleFactorY;
    public static int screenWIDTH;
    public static int screenHEIGHT;
    public static int WIDTH;
    public static int HEIGHT;
    private boolean shoot;
    private MainThread thread;
    private Background bg;
    private long bulletStartTime;
    public long timeDelayShoot = 0;
    private SpaceShip spaceShip;
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public GamePanel(Context context)
    {
        super(context);
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }
    public Rect getRectangle(int x, int y, int width, int height){
        return new Rect(x,y, x+width, y+height);
    }
    public boolean shoot(boolean shot){
        return shoot = shot;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        bg.setVector(5);
        WIDTH = bg.image.getWidth();
        HEIGHT = bg.image.getHeight();
        screenHEIGHT = getHeight();
        screenWIDTH = getWidth();
        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(),R.drawable.spaceship));
        System.out.println(spaceShip.getX());
        bulletStartTime = System.nanoTime();
        thread.setRunning(true);
        thread.start();

    }
    public void update()
    {
        spaceShip.update();
        bg.update();
        long elapsed = (System.nanoTime() - bulletStartTime)/1000000;
        if(shoot && elapsed > timeDelayShoot){
            bullets.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet),spaceShip.rect.centerX()-9,spaceShip.rect.top-spaceShip.height+10,-40, 0));
            bulletStartTime = System.nanoTime();
        }
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).update();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            rect = getRectangle(spaceShip.x, spaceShip.y,spaceShip.width, spaceShip.height);
            shoot(true);
            int x = (int)(event.getRawX()/copyscaleFactorX);
            int y = (int)(event.getRawY()/copyscaleFactorY);
            if(spaceShip.rect.contains(x,y)){
                spaceShip.moving(true);
        }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            int x = (int)(event.getRawX()/copyscaleFactorX);
            int y = (int)(event.getRawY()/copyscaleFactorY);
            rect = getRectangle(spaceShip.x, spaceShip.y,spaceShip.width, spaceShip.height);
            spaceShip.setXY(x,y);
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            shoot(false);
            spaceShip.moving(false);
            return  true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void draw(Canvas canvas)
    { super.draw(canvas);
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        copyscaleFactorY = scaleFactorY;
        copyscaleFactorX = scaleFactorX;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            spaceShip.draw(canvas);
            for(Bullet m:bullets){
                m.draw(canvas);
            }
            canvas.restoreToCount(savedState);
            copySavestate = savedState;
        }
    }
}