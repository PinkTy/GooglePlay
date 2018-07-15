package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

@SuppressLint("ViewConstructor")
public class store extends BaseWindow {
    private float back_x;
    private int widthOfSpaceShip,heightOfSpaceShip, shipSeclected, bulletSelected ;
    private float back_y;
    private Bitmap background;
    private Bitmap back;
    private float proportion_X, proportion_Y;
    private Rect rect;
    private Rect[] spaceShipRect = new Rect[16];
    private Rect[] spaceShipRectTouch = new Rect[16];
    private Rect[] bulletsRect = new Rect[16];
    private Rect[] bulletsRectTouch = new Rect[16];
    private  Paint paint1 = new Paint();
    private MediaPlayer mMediaPlayer;
    private Bitmap[] bitmaps = new Bitmap[16];
    private Bitmap[] bullets = new Bitmap[16];


    public store(Context context, GameSoundPool sounds) {
        super(context, sounds);
        paint1.setColor(Color.YELLOW);
        paint.setTextSize(40);
        paint.setColor(Color.RED);
        shipSeclected = 0;
        bulletSelected = 0;
        Load();
        System.out.println(shipSeclected);
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
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int)(event.getX()/scalex);
            int y = (int) (event.getY()/scaley);
            for (int i = 0; i <spaceShipRectTouch.length ; i++) {
                if (spaceShipRectTouch[i].contains(x, y)){
                    shipSeclected = i;
                }

            }
            for (int i = 0; i <bulletsRectTouch.length ; i++) {
                if (bulletsRectTouch[i].contains(x, y)){
                    bulletSelected = i;
                }
            }
        }
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
        widthOfSpaceShip = bitmaps[0].getWidth();
        heightOfSpaceShip =  bitmaps[0].getHeight();
        //
        bulletsRect[0] = new Rect(200,(int)(50 + 4*proportion_Y),200+bullets[0].getWidth(), (int)(50 + 4*proportion_Y)+bullets[0].getHeight());
        bulletsRect[1] = new Rect((int)(200 +1*proportion_X),(int)(50 + 4*proportion_Y),(int)((200 +1*proportion_X) +bullets[0].getWidth()),(int)(50 + 4*proportion_Y)+ bullets[0].getHeight());
        bulletsRect[2] = new Rect((int)(200 +2*proportion_X),(int)(50 + 4*proportion_Y),(int)((200 +2*proportion_X)+bullets[0].getWidth()),(int)(50 + 4*proportion_Y)+ bullets[0].getHeight());
        bulletsRect[3] = new Rect((int)(200 +3*proportion_X),(int)(50 + 4*proportion_Y),(int)((200 +3*proportion_X) +bullets[12].getWidth()),(int)(50 + 4*proportion_Y)+ bullets[12].getHeight());
        //
        bulletsRect[4] = new Rect(200,(int)(50 + 5*proportion_Y),200+bullets[4].getWidth(), (int)(50 + 5*proportion_Y)+bullets[4].getHeight());
        bulletsRect[5] = new Rect((int)(200 +1*proportion_X),(int)(50 + 5*proportion_Y),(int)((200 +1*proportion_X) +bullets[4].getWidth()),(int)(50 + 5*proportion_Y)+ bullets[4].getHeight());
        bulletsRect[6] = new Rect((int)(200 +2*proportion_X),(int)(50 + 5*proportion_Y),(int)((200 +2*proportion_X)+bullets[4].getWidth()),(int)(50 + 5*proportion_Y)+ bullets[4].getHeight());
        bulletsRect[7] = new Rect((int)(200 +3*proportion_X),(int)(50 + 5*proportion_Y),(int)((200 +3*proportion_X) +bullets[13].getWidth()),(int)(50 + 5*proportion_Y)+ bullets[13].getHeight());
        //
        bulletsRect[8] = new Rect(200,(int)(50 + 6*proportion_Y),200+bullets[6].getWidth(), (int)(50 + 6*proportion_Y)+bullets[6].getHeight());
        bulletsRect[9] = new Rect((int)(200 +1*proportion_X),(int)(50 + 6*proportion_Y),(int)((200 +1*proportion_X) +bullets[6].getWidth()),(int)(50 + 6*proportion_Y)+ bullets[6].getHeight());
        bulletsRect[10] = new Rect((int)(200 +2*proportion_X),(int)(50 + 6*proportion_Y),(int)((200 +2*proportion_X)+bullets[6].getWidth()),(int)(50 + 6*proportion_Y)+ bullets[6].getHeight());
        bulletsRect[11] = new Rect((int)(200 +3*proportion_X),(int)(50 + 6*proportion_Y),(int)((200 +3*proportion_X) +bullets[14].getWidth()),(int)(50 + 6*proportion_Y)+ bullets[14].getHeight());
        //

        bulletsRect[12] = new Rect(200,(int)(50 + 7*proportion_Y),200+bullets[9].getWidth(), (int)(50 + 7*proportion_Y)+heightOfSpaceShip);
        bulletsRect[13] = new Rect((int)(200 +1*proportion_X),(int)(50 + 7*proportion_Y),(int)((200 +1*proportion_X) +bullets[9].getWidth()),(int)(50 + 7*proportion_Y)+ bullets[9].getHeight());
        bulletsRect[14] = new Rect((int)(200 +2*proportion_X),(int)(50 + 7*proportion_Y),(int)((200 +2*proportion_X)+bullets[9].getWidth()),(int)(50 + 7*proportion_Y)+ bullets[9].getHeight());
        bulletsRect[15] = new Rect((int)(200 +3*proportion_X),(int)(50 + 7*proportion_Y),(int)((200 +3*proportion_X) +bullets[15].getWidth()),(int)(50 + 7*proportion_Y)+ bullets[15].getHeight());
        //


        bulletsRectTouch[0] = new Rect(200-100,(int)(50 + 4*proportion_Y),200+bullets[0].getWidth()+100, (int)(50 + 4*proportion_Y)+bullets[0].getHeight());
        bulletsRectTouch[1] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 4*proportion_Y),(int)((200 +1*proportion_X) +bullets[0].getWidth()+100),(int)(50 + 4*proportion_Y)+ bullets[0].getHeight());
        bulletsRectTouch[2] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 4*proportion_Y),(int)((200 +2*proportion_X)+bullets[0].getWidth()+100),(int)(50 + 4*proportion_Y)+ bullets[0].getHeight());
        bulletsRectTouch[3] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 4*proportion_Y),(int)((200 +3*proportion_X) +bullets[12].getWidth()+100),(int)(50 + 4*proportion_Y)+ bullets[12].getHeight());
        //
        bulletsRectTouch[4] = new Rect(200-100,(int)(50 + 5*proportion_Y),200+bullets[4].getWidth()+100, (int)(50 + 5*proportion_Y)+bullets[4].getHeight());
        bulletsRectTouch[5] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 5*proportion_Y),(int)((200 +1*proportion_X) +bullets[4].getWidth()+100),(int)(50 + 5*proportion_Y)+ bullets[4].getHeight());
        bulletsRectTouch[6] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 5*proportion_Y),(int)((200 +2*proportion_X)+bullets[4].getWidth()+100),(int)(50 + 5*proportion_Y)+ bullets[4].getHeight());
        bulletsRectTouch[7] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 5*proportion_Y),(int)((200 +3*proportion_X) +bullets[13].getWidth()+100),(int)(50 + 5*proportion_Y)+ bullets[13].getHeight());
        //
        bulletsRectTouch[8] = new Rect(200-100,(int)(50 + 6*proportion_Y),200+bullets[6].getWidth()+100, (int)(50 + 6*proportion_Y)+bullets[6].getHeight());
        bulletsRectTouch[9] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 6*proportion_Y),(int)((200 +1*proportion_X) +bullets[6].getWidth()+100),(int)(50 + 6*proportion_Y)+ bullets[6].getHeight());
        bulletsRectTouch[10] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 6*proportion_Y),(int)((200 +2*proportion_X)+bullets[6].getWidth()+100),(int)(50 + 6*proportion_Y)+ bullets[6].getHeight());
        bulletsRectTouch[11] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 6*proportion_Y),(int)((200 +3*proportion_X) +bullets[14].getWidth()+100),(int)(50 + 6*proportion_Y)+ bullets[14].getHeight());
        //

        bulletsRectTouch[12] = new Rect(200-100,(int)(50 + 7*proportion_Y),200+bullets[9].getWidth()+100, (int)(50 + 7*proportion_Y)+heightOfSpaceShip);
        bulletsRectTouch[13] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 7*proportion_Y),(int)((200 +1*proportion_X) +bullets[9].getWidth()+100),(int)(50 + 7*proportion_Y)+ bullets[9].getHeight());
        bulletsRectTouch[14] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 7*proportion_Y),(int)((200 +2*proportion_X)+bullets[9].getWidth()+100),(int)(50 + 7*proportion_Y)+ bullets[9].getHeight());
        bulletsRectTouch[15] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 7*proportion_Y),(int)((200 +3*proportion_X) +bullets[15].getWidth()+100),(int)(50 + 7*proportion_Y)+ bullets[15].getHeight());






        spaceShipRect[0] = new Rect(200,50,200+widthOfSpaceShip, 50+heightOfSpaceShip);
        spaceShipRect[1] = new Rect((int)(200 +1*proportion_X),50,(int)((200 +1*proportion_X) +widthOfSpaceShip),50+ heightOfSpaceShip);
        spaceShipRect[2] = new Rect((int)(200 +2*proportion_X),50,(int)((200 +2*proportion_X)+widthOfSpaceShip),50+ heightOfSpaceShip);
        spaceShipRect[3] = new Rect((int)(200 +3*proportion_X),50,(int)((200 +3*proportion_X) +widthOfSpaceShip),50+ heightOfSpaceShip);
        //
        spaceShipRect[4] = new Rect(200,(int)(50 + 1*proportion_Y),200+widthOfSpaceShip, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[5] = new Rect((int)(200 +1*proportion_X),(int)(50 + 1*proportion_Y),(int)(200 +1*proportion_X)+widthOfSpaceShip, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[6] = new Rect((int)(200 +2*proportion_X),(int)(50 + 1*proportion_Y),(int)(200 +2*proportion_X)+widthOfSpaceShip, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[7] = new Rect((int)(200 +3*proportion_X),(int)(50 + 1*proportion_Y),(int)(200 +3*proportion_X)+widthOfSpaceShip, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        //
        spaceShipRect[8] = new Rect(200,(int)(50 + 2*proportion_Y),200+widthOfSpaceShip, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[9] = new Rect((int)(200 +1*proportion_X),(int)(50 + 2*proportion_Y),(int)(200 +1*proportion_X)+widthOfSpaceShip, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[10] = new Rect((int)(200 +2*proportion_X),(int)(50 + 2*proportion_Y),(int)(200 +2*proportion_X)+widthOfSpaceShip, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[11] = new Rect((int)(200 +3*proportion_X),(int)(50 + 2*proportion_Y),(int)(200 +3*proportion_X)+widthOfSpaceShip, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        //
        spaceShipRect[12] = new Rect(200,(int)(50 + 3*proportion_Y),200+widthOfSpaceShip, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[13] = new Rect((int)(200 +1*proportion_X),(int)(50 + 3*proportion_Y),(int)(200 +1*proportion_X)+widthOfSpaceShip, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[14] = new Rect((int)(200 +2*proportion_X),(int)(50 + 3*proportion_Y),(int)(200 +2*proportion_X)+widthOfSpaceShip, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
        spaceShipRect[15] = new Rect((int)(200 +3*proportion_X),(int)(50 + 3*proportion_Y),(int)(200 +3*proportion_X)+widthOfSpaceShip, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);



        spaceShipRectTouch[0] = new Rect(200-100,50,200+widthOfSpaceShip+100, 50+heightOfSpaceShip);
        spaceShipRectTouch[1] = new Rect((int)(200-100 +1*proportion_X),50,(int)((200 +1*proportion_X) +widthOfSpaceShip+100),50+ heightOfSpaceShip);
        spaceShipRectTouch[2] = new Rect((int)(200-100 +2*proportion_X),50,(int)((200 +2*proportion_X)+widthOfSpaceShip+100),50+ heightOfSpaceShip);
        spaceShipRectTouch[3] = new Rect((int)(200-100 +3*proportion_X),50,(int)((200 +3*proportion_X) +widthOfSpaceShip+100),50+ heightOfSpaceShip);
        //
        spaceShipRectTouch[4] = new Rect(200-100,(int)(50 + 1*proportion_Y),200+widthOfSpaceShip+100, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[5] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 1*proportion_Y),(int)(200 +1*proportion_X)+widthOfSpaceShip+100, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[6] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 1*proportion_Y),(int)(200 +2*proportion_X)+widthOfSpaceShip+100, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[7] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 1*proportion_Y),(int)(200 +3*proportion_X)+widthOfSpaceShip+100, (int)(50 + 1*proportion_Y)+heightOfSpaceShip);
        //
        spaceShipRectTouch[8] = new Rect(200-100,(int)(50 + 2*proportion_Y),200+widthOfSpaceShip+100, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[9] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 2*proportion_Y),(int)(200 +1*proportion_X)+widthOfSpaceShip+100, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[10] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 2*proportion_Y),(int)(200 +2*proportion_X)+widthOfSpaceShip+100, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[11] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 2*proportion_Y),(int)(200 +3*proportion_X)+widthOfSpaceShip+100, (int)(50 + 2*proportion_Y)+heightOfSpaceShip);
        //
        spaceShipRectTouch[12] = new Rect(200-100,(int)(50 + 3*proportion_Y),200+widthOfSpaceShip+100, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[13] = new Rect((int)(200-100 +1*proportion_X),(int)(50 + 3*proportion_Y),(int)(200 +1*proportion_X)+widthOfSpaceShip+100, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[14] = new Rect((int)(200-100 +2*proportion_X),(int)(50 + 3*proportion_Y),(int)(200 +2*proportion_X)+widthOfSpaceShip+100, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
        spaceShipRectTouch[15] = new Rect((int)(200-100 +3*proportion_X),(int)(50 + 3*proportion_Y),(int)(200 +3*proportion_X)+widthOfSpaceShip+100, (int)(50 + 3*proportion_Y)+heightOfSpaceShip);
    }








    public void Save(){
        switch (shipSeclected){
            case 1:
//                ConstantUtil.SPACESHIP_STYLE = 1;
        }
    }
    public void Load(){
        switch (ConstantUtil.SPACESHIP_STYLE){
            case 1:
                switch (ConstantUtil.SHIP_COlOR){
                    case 1:
                        shipSeclected = 0;
                        break;
                    case 2:
                        shipSeclected = 1;
                        break;
                    case 3:
                        shipSeclected = 2;
                        break;
                    case 4:
                        shipSeclected = 3;
                        break;
                }
                break;
            case 2:
                switch (ConstantUtil.SHIP_COlOR){
                    case 1:
                        shipSeclected = 4;
                        break;
                    case 2:
                        shipSeclected = 5;
                        break;
                    case 3:
                        shipSeclected = 6;
                        break;
                    case 4:
                        shipSeclected = 7;
                        break;
                }
                break;
            case 3:
                switch (ConstantUtil.SHIP_COlOR){
                    case 1:
                        shipSeclected = 8;
                        break;
                    case 2:
                        shipSeclected = 9;
                        break;
                    case 3:
                        shipSeclected = 10;
                        break;
                    case 4:
                        shipSeclected = 11;
                        break;
                }
                break;
            case 4:
                switch (ConstantUtil.SHIP_COlOR){
                    case 1:
                        shipSeclected = 12;
                        break;
                    case 2:
                        shipSeclected = 13;
                        break;
                    case 3:
                        shipSeclected = 14;
                        break;
                    case 4:
                        shipSeclected = 15;
                        break;
                }
                break;

        }
        switch (ConstantUtil.BULLET_STYLE){
            case 1:
                switch (ConstantUtil.BULLET_COlOR){
                    case 1:
                        bulletSelected = 0;
                        break;
                    case 2:
                        bulletSelected = 1;
                        break;
                    case 3:
                        bulletSelected = 2;
                        break;
                    case 4:
                        bulletSelected = 3;
                        break;
                }
                break;
            case 2:
                switch (ConstantUtil.BULLET_COlOR){
                    case 1:
                        bulletSelected = 4;
                        break;
                    case 2:
                        bulletSelected = 5;
                        break;
                    case 3:
                        bulletSelected = 6;
                        break;
                    case 4:
                        bulletSelected = 7;
                        break;
                }
                break;
            case 3:
                switch (ConstantUtil.BULLET_COlOR){
                    case 1:
                        bulletSelected = 8;
                        break;
                    case 2:
                        bulletSelected = 9;
                        break;
                    case 3:
                        bulletSelected = 10;
                        break;
                    case 4:
                        bulletSelected = 11;
                        break;
                }
                break;
            case 4:
                switch (ConstantUtil.BULLET_COlOR){
                    case 1:
                        bulletSelected = 12;
                        break;
                    case 2:
                        bulletSelected = 13;
                        break;
                    case 3:
                        bulletSelected = 14;
                        break;
                    case 4:
                        bulletSelected = 15;
                        break;
                }
                break;

        }

    }


    @Override
    public void drawSelf() {
        try {

            canvas = sfh.lockCanvas();
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
//            for (int i = 0; i <bulletsRectTouch.length ; i++) {
//                canvas.drawRect(bulletsRectTouch[i], paint1);
//            }
//            for (int i = 0; i <bulletsRectTouch.length ; i++) {
//                canvas.drawRect(spaceShipRectTouch[i], paint1);
//            }

                canvas.drawRect(spaceShipRect[shipSeclected], paint);

                canvas.drawRect(bulletsRect[bulletSelected], paint);

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
