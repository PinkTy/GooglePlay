package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

@SuppressLint("ViewConstructor")
public class store extends BaseWindow {
    private float back_x;
    private float back_y;
    private Bitmap background;
    private Bitmap back;
    private Rect rect;
    private MediaPlayer mMediaPlayer;

    public store(Context context, GameSoundPool sounds) {
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
            if (x > back_x && x < back_x + back.getWidth()
                    && y > back_y && y < back_y + back.getHeight()) {
                mMediaPlayer.start();
                drawSelf();
                game.getHandler().sendEmptyMessage(ConstantUtil.TO_MENU_PANEL);
            }
            return true;
        }
        return false;
    }

    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        back = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        back_x = screen_width / 2 - back.getWidth() / 2;
        back_y = screen_height / 2 + 500;
    }

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();
            canvas.drawBitmap(back, back_x, back_y, paint);
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
