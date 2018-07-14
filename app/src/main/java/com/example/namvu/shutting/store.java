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
    private float proportion_X, proportion_Y;
    private Rect rect;
    private MediaPlayer mMediaPlayer;
    private Bitmap[] bitmaps = new Bitmap[16];
    private Bitmap[] bullets = new Bitmap[16];


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


        bitmaps[0] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1blue);
        bitmaps[1] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1red);
        bitmaps[2] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1green);
        bitmaps[3] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1orange);
        bitmaps[4] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2blue);
        bitmaps[5] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2red);
        bitmaps[6] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2green);
        bitmaps[7] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2orange);
        bitmaps[8] =BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
        bitmaps[9] =BitmapFactory.decodeResource(getResources(), R.drawable.playership3red);
        bitmaps[10] =BitmapFactory.decodeResource(getResources(), R.drawable.playership3green);
        bitmaps[11] =BitmapFactory.decodeResource(getResources(), R.drawable.playership3orange);
        bitmaps[12] =BitmapFactory.decodeResource(getResources(), R.drawable.ufoblue);
        bitmaps[13] =BitmapFactory.decodeResource(getResources(), R.drawable.ufored);
        bitmaps[14] =BitmapFactory.decodeResource(getResources(), R.drawable.ufogreen);
        bitmaps[15] =BitmapFactory.decodeResource(getResources(), R.drawable.ufoyellow);
        //
        bullets[0] =BitmapFactory.decodeResource(getResources(), R.drawable.laserred1);
        bullets[1] =BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen1);
        bullets[2] =BitmapFactory.decodeResource(getResources(), R.drawable.laserblue1);
        bullets[3] =BitmapFactory.decodeResource(getResources(), R.drawable.laserred2);
        bullets[4] =BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen2);
        bullets[5] =BitmapFactory.decodeResource(getResources(), R.drawable.laserblue2);
        bullets[6] =BitmapFactory.decodeResource(getResources(), R.drawable.laserred3);
        bullets[7] =BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen3);
        bullets[8] =BitmapFactory.decodeResource(getResources(), R.drawable.laserblue3);
        bullets[9] =BitmapFactory.decodeResource(getResources(), R.drawable.laserred4);
        bullets[10] =BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen4);
        bullets[11] =BitmapFactory.decodeResource(getResources(), R.drawable.laserblue4);
        bullets[12] =BitmapFactory.decodeResource(getResources(), R.drawable.mix1);
        bullets[13] =BitmapFactory.decodeResource(getResources(), R.drawable.mix2);
        bullets[14] =BitmapFactory.decodeResource(getResources(), R.drawable.mix3);
        bullets[15] =BitmapFactory.decodeResource(getResources(), R.drawable.mix4);

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
        proportion_X = ((background.getWidth() -100) /4);
        proportion_Y = ((background.getHeight() - 100)/9);
    }

    @Override
    public void drawSelf() {
        try {

            canvas = sfh.lockCanvas();
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.drawBitmap(bitmaps[0], 200, 50, paint);
            canvas.drawBitmap(bitmaps[1], 200 +1*proportion_X, 50 , paint);
            canvas.drawBitmap(bitmaps[2], 200 +2*proportion_X, 50 , paint);
            canvas.drawBitmap(bitmaps[3], 200 +3*proportion_X, 50 , paint);
            //
            canvas.drawBitmap(bitmaps[4], 200, 50 + 1*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[5], 200 +1*proportion_X, 50 + 1*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[6], 200 +2*proportion_X, 50 + 1*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[7], 200 +3*proportion_X, 50 + 1*proportion_Y, paint);
            //
            canvas.drawBitmap(bitmaps[8], 200, 50+ 2*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[9], 200 +1*proportion_X, 50 + 2*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[10], 200 +2*proportion_X, 50 + 2*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[11], 200 +3*proportion_X, 50 + 2*proportion_Y, paint);
            //
            canvas.drawBitmap(bitmaps[12], 200, 50+ 3*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[13], 200 +1*proportion_X, 50 + 3*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[14], 200 +2*proportion_X, 50 + 3*proportion_Y, paint);
            canvas.drawBitmap(bitmaps[15], 200 +3*proportion_X, 50 + 3*proportion_Y, paint);
            //
            canvas.drawBitmap(bullets[0], 200, 50+ 4*proportion_Y, paint);
            canvas.drawBitmap(bullets[1], 200 +1*proportion_X, 50 + 4*proportion_Y, paint);
            canvas.drawBitmap(bullets[2], 200 +2*proportion_X, 50 + 4*proportion_Y, paint);
            canvas.drawBitmap(bullets[12], 200 +3*proportion_X, 50 + 4*proportion_Y, paint);
            //
            canvas.drawBitmap(bullets[3], 200, 50+ 5*proportion_Y, paint);
            canvas.drawBitmap(bullets[4], 200 +1*proportion_X, 50 + 5*proportion_Y, paint);
            canvas.drawBitmap(bullets[5], 200 +2*proportion_X, 50 + 5*proportion_Y, paint);
            canvas.drawBitmap(bullets[13], 200 +3*proportion_X, 50 + 5*proportion_Y, paint);
            //
            canvas.drawBitmap(bullets[6], 200, 50+ 6*proportion_Y, paint);
            canvas.drawBitmap(bullets[7], 200 +1*proportion_X, 50 + 6*proportion_Y, paint);
            canvas.drawBitmap(bullets[8], 200 +2*proportion_X, 50 + 6*proportion_Y, paint);
            canvas.drawBitmap(bullets[14], 200 +3*proportion_X, 50 + 6*proportion_Y, paint);
            //
            canvas.drawBitmap(bullets[9], 200, 50+ 7*proportion_Y, paint);
            canvas.drawBitmap(bullets[10], 200 +1*proportion_X, 50 + 7*proportion_Y, paint);
            canvas.drawBitmap(bullets[11], 200 +2*proportion_X, 50 + 7*proportion_Y, paint);
            canvas.drawBitmap(bullets[15], 200 +3*proportion_X, 50 + 7*proportion_Y, paint);

            //

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
