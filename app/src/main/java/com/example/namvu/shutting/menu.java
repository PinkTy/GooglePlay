package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;

@SuppressLint("ViewConstructor")
public class menu extends BaseWindow{
    private float button_x;
    private float button_y;
    private float strwid;
    private float strhei;
    private boolean isBtChange;
    private String startGame = "Start Game";
    private Bitmap button;
    private Bitmap background;
    private Rect rect;

    public menu(Context context, GameSoundPool sounds) {
        super(context, sounds);
        paint.setTextSize(40);
        rect = new Rect();
        thread = new Thread(this);
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

    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        //button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();

        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 + button.getHeight();

        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        strwid = rect.width();
        strhei = rect.height();
    }


    @Override
    public void release() {
        if (!background.isRecycled()) {
            background.recycle();
        }
    }
    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();
//            if (isBtChange) {
//                canvas.drawBitmap(button2, button_x, button_y, paint);
//            } else {
//                canvas.drawBitmap(button, button_x, button_y, paint);
//            }
//            if (isBtChange2) {
//                canvas.drawBitmap(button2, button_x, button_y2, paint);
//            } else {
//                canvas.drawBitmap(button, button_x, button_y2, paint);
//            }
//
//
//            paint.setColor(Color.BLACK);
//            canvas.drawText(startGame, screen_width / 2 - strwid / 2, button_y
//                    + button.getHeight() / 2 + strhei / 2, paint);
//
//            canvas.drawText(exitGame, screen_width / 2 - strwid / 2, button_y2
//                    + button.getHeight() / 2 + strhei / 2, paint);

          //  canvas.restore();
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
