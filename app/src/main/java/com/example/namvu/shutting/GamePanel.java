package com.example.namvu.shutting;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


public class GamePanel extends BaseWindow implements SurfaceHolder.Callback
{
    public Dictionary<Integer, Bullet> dictionaryBullets = new Hashtable<>();
    Integer countBullets = 0;
    private Random rand = new Random();
    public Rect rect;
    Bitmap[] rockImage = new Bitmap[10];
    Bitmap[] explosionImageR = new Bitmap[9];
    Bitmap[] explosionImageRSmall = new Bitmap[9];
    Bitmap[] explosionImageSonic = new Bitmap[9];
    Bitmap[] explosionImageSonicSmall = new Bitmap[9];
    public static int copySavestate;
    public static float copyscaleFactorX;
    public static float copyscaleFactorY;
    public static int screenWIDTH;
    public static int screenHEIGHT;
    public static int WIDTH;
    public static int HEIGHT;
    private boolean shoot, delete;
    private MainThread thread;
    private Background bg;
    private long bulletStartTime;
    public long timeDelayShoot = 0;
    private SpaceShip spaceShip;
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public static ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    public static ArrayList<Rock> rocks = new ArrayList<Rock>();
    private MediaPlayer mMediaPlayer;
    public GamePanel(Context context, GameSoundPool sounds)
    {
        super(context,sounds);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        //make gamePanel focusable so it can handle events
        setFocusable(true);

        //music
        mMediaPlayer = MediaPlayer.create(game, R.raw.testmusic);
        mMediaPlayer.setLooping(true);
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
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
    // this function create a new Rock. the fall from the top of the screen
    public void newRock(Bitmap[] rockimages){

        int i = 0 + rand.nextInt(10 -  1);
        rocks.add(new Rock(rockimages[i]));
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        bg.setVector(5);
        WIDTH = bg.image.getWidth();
        HEIGHT = bg.image.getHeight();
        copyscaleFactorY = getHeight()/(HEIGHT*1.f);
        copyscaleFactorX = getWidth()/(WIDTH*1.f);
        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(),R.drawable.spaceship),BitmapFactory.decodeResource(getResources(),R.drawable.shield));
        bulletStartTime = System.nanoTime();
        // load rock bitmap

        rockImage[0] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownbigfour);
        rockImage[1] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownbigone);
        rockImage[2] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownbigtwo);
        rockImage[3] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownbigthree);
        rockImage[4] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownmedone);
        rockImage[5] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownmedtwo);
        rockImage[6] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownsmallone);
        rockImage[7] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrownsmalltwo);
        rockImage[8] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrowntinyone);
        rockImage[9] = BitmapFactory.decodeResource(getResources(),R.drawable.meteorbrowntinytwo);

        // load Regular Explosion
        explosionImageR[0] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion00);
        explosionImageR[1] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion01);
        explosionImageR[2] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion02);
        explosionImageR[3] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion03);
        explosionImageR[4] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion04);
        explosionImageR[5] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion05);
        explosionImageR[6] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion06);
        explosionImageR[7] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion07);
        explosionImageR[8] = BitmapFactory.decodeResource(getResources(),R.drawable.regularexplosion08);
        for(int i = 0; i < explosionImageR.length; i++) {
            int w = explosionImageR[i].getWidth()/3;
            int h = explosionImageR[i].getHeight()/3;
            explosionImageRSmall[i] = Bitmap.createScaledBitmap(explosionImageR[i],w,h, false);
        }


        // load Sonic Explosion
        explosionImageSonic[0] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion00);
        explosionImageSonic[1] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion01);
        explosionImageSonic[2] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion02);
        explosionImageSonic[3] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion03);
        explosionImageSonic[4] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion04);
        explosionImageSonic[5] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion05);
        explosionImageSonic[6] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion06);
        explosionImageSonic[7] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion07);
        explosionImageSonic[8] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicexplosion08);
        for(int i = 0; i < explosionImageSonic.length; i++) {
            int w = explosionImageSonic[i].getWidth()/2;
            int h = explosionImageSonic[i].getHeight()/2;
            explosionImageSonicSmall[i] = Bitmap.createScaledBitmap(explosionImageSonic[i],w,h, false);
        }

        // create 50 rocks at the start of the game
        for(int n=0;n < 5; n++) {
            newRock(rockImage);
        }


        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }


    public void update()
    {
        spaceShip.update();
        bg.update();
        // This is using for update the bullet
        // counting time delay for shoot

        long elapsed = (System.nanoTime() - bulletStartTime)/1000000;
        if(shoot && elapsed > timeDelayShoot){
//
            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet),spaceShip.rect.centerX()-9,spaceShip.rect.top-spaceShip.height+10,-40, 0));
            bulletStartTime = System.nanoTime();
            countBullets += 1;

        }

        for (Enumeration e = dictionaryBullets.keys(); e.hasMoreElements();) {
            Object object = e.nextElement();
            delete = false;
            for(int n = 0; n < rocks.size(); n++) {

                if (collision(rocks.get(n), dictionaryBullets.get(object))) {
                    spaceShip.score += rocks.get(n).width;
                    explosions.add(new Explosion(explosionImageRSmall,rocks.get(n).rectangle.centerX(),rocks.get(n).rectangle.centerY()));
                    delete = true;
                    dictionaryBullets.remove(object);
                    rocks.remove(n);

                    newRock(rockImage);
                    break;
                }
            }

            if(!delete){


            if(dictionaryBullets.get(object).y<-100 ){
               dictionaryBullets.remove(object);


            }
            else{

                    dictionaryBullets.get(object).update();
                }

            }

        }



        for(int i = 0; i < rocks.size(); i++){
            rocks.get(i).update();
            // check the collision between rocks and the ship it work but a bit lag
            if(collision(rocks.get(i), spaceShip)){
                spaceShip.health -= rocks.get(i).width/2;
                if (spaceShip.health<0) {
                    explosions.add(new Explosion(explosionImageSonicSmall,spaceShip.x-60,spaceShip.rect.top-90));
                    spaceShip.health =0;
                    spaceShip.moving(false);
                spaceShip.setY(-500);}
                spaceShip.score += rocks.get(i).width;
                explosions.add(new Explosion(explosionImageRSmall,rocks.get(i).rectangle.centerX(),rocks.get(i).rectangle.centerY()));
                rocks.remove(i);
                newRock(rockImage);
//                layer.setPlaying(false);


            }
//            for(int n = 0; n < bullets.size(); i++){
//                if(collision(bullets.get(n), rocks.get(i))){
//                    bullets.remove(n);
//                    rocks.remove(i);}}
//
        }
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).remove){
                explosions.remove(i);
            }
        }
    }
    // collision function. that check the collision between 2 object of GameObject
    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle())){
            return true;
        }
        return  false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {//
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int)(event.getRawX()/copyscaleFactorX);
            int y = (int)(event.getRawY()/copyscaleFactorY);
            shoot(true);
            if (spaceShip.rectFortouch.contains(x,y)){
                spaceShip.setXY(x,y);
                spaceShip.moving(true);
            }

            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE && spaceShip.move){
            int x = (int)(event.getRawX()/copyscaleFactorX);
            int y = (int)(event.getRawY()/copyscaleFactorY);
            if(x+spaceShip.width > WIDTH){
                x = WIDTH-spaceShip.width;
            }
            if(y > HEIGHT -spaceShip.height){
                y = HEIGHT -spaceShip.height;
            }
            if(spaceShip.rectFortouch.contains(x,y)){
                spaceShip.moving(true);
            }
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
    public void drawText(Canvas canvas, int shipHealth){
        Paint paint = new Paint();

        float green  = (shipHealth*1.f/100*1.f)*225;
        float red  = (1-(shipHealth*1.f/100*1.f))*225 ;
        int barLength = (int)  ((shipHealth*1.f/100*1.f)*200);
//        if (green < 0) green =0;
//        if (red > 225) red =225;
//        if (barLength < 0) barLength =0;

        paint.setColor(Color.rgb((int) red,(int) green,0));
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setTextSize(40);
        paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(""+spaceShip.score, WIDTH/2-50, 40, paint1);


        int barHeight = 10;
        float fill = (shipHealth*1.f/100*1.f)* (barLength*1.f);
        Rect barHealth = new Rect(5, 5,5+ barLength, barHeight);


        canvas.drawRect(barHealth, paint);
        canvas.drawLine(5, 4, 5+ 200+1, 4,paint);
        canvas.drawLine(5, barHeight, 5+ 200+1, barHeight,paint);
        canvas.drawLine(5, 4, 5, barHeight,paint);
        canvas.drawLine(5+ 200+1, barHeight, 5+ 200+1, 4,paint);



    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
//        System.out.println(getWidth());
//        System.out.println(getHeight());
//        System.out.println(scaleFactorX);
//        System.out.println(scaleFactorY);
//
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            spaceShip.draw(canvas);
            for (Enumeration e = dictionaryBullets.keys(); e.hasMoreElements();) {
                dictionaryBullets.get(e.nextElement()).draw(canvas);
            }

            // update rock
            for(Rock r:rocks){
                r.draw(canvas);
            }
            for(Explosion e:explosions){
                e.draw(canvas);
            }
            drawText(canvas, spaceShip.health);
            canvas.restoreToCount(savedState);
            copySavestate = savedState;
        }
    }
}