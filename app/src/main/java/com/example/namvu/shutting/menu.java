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
public class menu extends BaseWindow{
    private float button_x;
    private float button_y;
    private float button1_x;
    private float button1_y;
    private float line_x;
    private float line_y;
    private float line1_x;
    private float line1_y;
    private float frame_x;
    private float frame_y;
    private boolean isBtChange;
    private Bitmap newGame;
    private Bitmap line;
    private Bitmap line1;
    private Bitmap background;
    private Bitmap store;
    private Bitmap frame;
    private Rect rect;
    private MediaPlayer mMediaPlayer;

    public menu(Context context, GameSoundPool sounds) {
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
            if (x > button_x && x < button_x + newGame.getWidth()
                    && y > button_y && y < button_y + newGame.getHeight()) {
                mMediaPlayer.start();
                isBtChange = true;
                drawSelf();
                game.getHandler().sendEmptyMessage(ConstantUtil.TO_CHOOSE_PANEL);
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + newGame.getWidth()
                    && y > button_y && y < button_y + newGame.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            return true;
        }
        return false;
    }

    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        newGame = BitmapFactory.decodeResource(getResources(), R.drawable.newgame);
        store = BitmapFactory.decodeResource(getResources(), R.drawable.store);
        line = BitmapFactory.decodeResource(getResources(), R.drawable.line);
        line1 = BitmapFactory.decodeResource(getResources(), R.drawable.line);
        frame = BitmapFactory.decodeResource(getResources(), R.drawable.blueframe);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();

        button_x = screen_width / 2 - newGame.getWidth() / 2;
        button_y = screen_height / 2 + newGame.getHeight();
        frame_x = button_x - 35;
        frame_y = button_y - 10;
        line_x = button_x + 7;
        line_y = button_y + 95;
        button1_x = button_x + 75;
        button1_y = button_y + 215;
        line1_x = line_x;
        line1_y = line_y + 215;
    }

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();
            if(isBtChange){
                canvas.drawBitmap(frame, frame_x, frame_y, paint);
                canvas.drawBitmap(newGame, button_x, button_y, paint);
                canvas.drawBitmap(line, line_x, line_y, paint);
            }
            else{
                canvas.drawBitmap(newGame, button_x, button_y, paint);
                canvas.drawBitmap(line, line_x, line_y, paint);
            }
            canvas.drawBitmap(store, button1_x, button1_y, paint);
            canvas.drawBitmap(line, line1_x, line1_y, paint);
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
