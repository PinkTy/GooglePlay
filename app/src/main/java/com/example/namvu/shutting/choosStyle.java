package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

@SuppressLint("ViewConstructor")
public class choosStyle extends BaseWindow {
    private float blueCircle_x;
    private float blueCircle_y;
    private float greenCircle_x;
    private float greenCircle_y;
    private float orangeCircle_x;
    private float orangeCircle_y;
    private float fight_x;
    private float fight_y;
    private float explore_x;
    private float explore_y;
    private float flight_x;
    private float flight_y;
    private float ship1_x;
    private float ship1_y;
    private float ship2_x;
    private float ship2_y;
    private float ship3_x;
    private float ship3_y;
    private String choose = "Please choose the game mode: ";
    private boolean isBtChange;
    private boolean isBtChange2;
    private boolean isBtChange3;
    private Bitmap blueCircle;
    private Bitmap greenCircle;
    private Bitmap orangeCircle;
    private Bitmap background;
    private Bitmap explore;
    private Bitmap fight;
    private Bitmap flight;
    private Bitmap ship1;
    private Bitmap ship2;
    private Bitmap ship3;
    private Rect rect;
    private MediaPlayer mMediaPlayer;

    public choosStyle(Context context, GameSoundPool sounds) {
        super(context, sounds);
        paint.setTextSize(40);
        rect = new Rect();
        thread = new Thread(this);
        mMediaPlayer = MediaPlayer.create(game, R.raw.button);
        mMediaPlayer.setLooping(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        super.surfaceCreated(arg0);
        initBitmap();
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        super.surfaceDestroyed(arg0);
        release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();
            if (x > explore_x && x < explore_x + explore.getWidth()
                    && y > explore_y && y < explore_y + explore.getHeight()) {
                mMediaPlayer.start();
                isBtChange = true;
                drawSelf();
                GamePanel.STYLE = 1;
                game.getHandler().sendEmptyMessage(ConstantUtil.TO_GAME_PANEL);
            }else if (x > fight_x && x < fight_x + fight.getWidth()
                    && y > fight_y && y < fight_y + fight.getHeight()) {
                mMediaPlayer.start();
                isBtChange2 = true;
                drawSelf();
                GamePanel.STYLE = 2;
                game.getHandler().sendEmptyMessage(ConstantUtil.TO_GAME_PANEL);
            }else if (x > flight_x && x < flight_x + flight.getWidth()
                    && y > flight_y && y < flight_y + fight.getHeight()) {
                mMediaPlayer.start();
                isBtChange3 = true;
                drawSelf();
                GamePanel.STYLE = 3;
                game.getHandler().sendEmptyMessage(ConstantUtil.TO_GAME_PANEL);
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > explore_x && x < explore_x + explore.getWidth()
                    && y > explore_y && y < explore_y + explore.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            if (x > fight_x && x < fight_x + fight.getWidth()
                    && y > fight_y && y < fight_y + fight.getHeight()) {
                isBtChange2 = true;
            } else {
                isBtChange2 = false;
            }
            if (x > flight_x && x < flight_x + flight.getWidth()
                    && y > flight_y && y < flight_y + fight.getHeight()) {
                isBtChange3 = true;
            } else {
                isBtChange3 = false;
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            isBtChange2 = false;
            isBtChange3 = false;
            return true;
        }
        return false;
    }

    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        blueCircle = BitmapFactory.decodeResource(getResources(), R.drawable.bluecircle);
        greenCircle = BitmapFactory.decodeResource(getResources(), R.drawable.greencircle);
        orangeCircle = BitmapFactory.decodeResource(getResources(), R.drawable.orangecircle);
        explore = BitmapFactory.decodeResource(getResources(), R.drawable.explore);
        fight = BitmapFactory.decodeResource(getResources(), R.drawable.fight);
        flight = BitmapFactory.decodeResource(getResources(), R.drawable.flight);
        ship1 = BitmapFactory.decodeResource(getResources(), R.drawable.playership1blue);
        ship2 = BitmapFactory.decodeResource(getResources(), R.drawable.playership1green);
        ship3 = BitmapFactory.decodeResource(getResources(), R.drawable.playership1orange);

        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        blueCircle_x = screen_width / 4 - blueCircle.getWidth() + 50;
        blueCircle_y = screen_height / 2 - blueCircle.getHeight();
        greenCircle_x = screen_width / 4 +  blueCircle.getWidth() - 40;
        greenCircle_y = blueCircle_y;
        orangeCircle_x =  screen_width / 2 +  blueCircle.getWidth();
        orangeCircle_y = blueCircle_y;

        explore_x = blueCircle_x - 5;
        explore_y = blueCircle_y + blueCircle.getHeight() + 30;
        fight_x = greenCircle_x - 5;
        fight_y = explore_y;
        flight_x = orangeCircle_x - 5;
        flight_y = explore_y;
        ship1_x = blueCircle_x + 42;
        ship1_y = blueCircle_y + 50;
        ship2_x = greenCircle_x + 42;
        ship2_y = greenCircle_y + 50;
        ship3_x = orangeCircle_x + 42;
        ship3_y = orangeCircle_y + 50;
    }

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();
            canvas.drawBitmap(blueCircle, blueCircle_x, blueCircle_y, paint);
            canvas.drawBitmap(explore, explore_x, explore_y, paint);
            canvas.drawBitmap(greenCircle, greenCircle_x, greenCircle_y, paint);
            canvas.drawBitmap(fight, fight_x, fight_y, paint);
            canvas.drawBitmap(orangeCircle, orangeCircle_x, orangeCircle_y, paint);
            canvas.drawBitmap(flight, flight_x, flight_y, paint);
            canvas.drawBitmap(ship1, ship1_x, ship1_y, paint);
            canvas.drawBitmap(ship2, ship2_x, ship2_y, paint);
            canvas.drawBitmap(ship3, ship3_x, ship3_y, paint);
            //            paint.setColor(Color.WHITE);
//            canvas.drawText(choose, screen_width / 2 -  blueCircle_x/ 2, blueCircle_y, paint);
            canvas.restore();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }
}
