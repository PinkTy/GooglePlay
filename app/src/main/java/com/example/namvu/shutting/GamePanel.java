package com.example.namvu.shutting;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    public static int copySavestate;
    public static float copyscaleFactorX;
    public static float copyscaleFactorY;
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
        mMediaPlayer = MediaPlayer.create(game, R.raw.backgroundmusic);
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
        //spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(),R.drawable.spaceship),BitmapFactory.decodeResource(getResources(),R.drawable.shield));
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
        // create 50 rocks at the start of the game
        for(int n=0;n < 50; n++) {
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
            //bullets.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet),spaceShip.rect.centerX()-9,spaceShip.rect.top-spaceShip.height+10,-40, 0));
            bulletStartTime = System.nanoTime();
            countBullets += 1;

        }

        for (Enumeration e = dictionaryBullets.keys(); e.hasMoreElements();) {
            Object object = e.nextElement();
            if(dictionaryBullets.get(object).y<-100 ){
               dictionaryBullets.remove(object);

            }
            else{
                dictionaryBullets.get(object).update();
            }


        }
//        for(int i = 0; i < bullets.size(); i++){
//            // this is trying to revome the bullets if the bullets out of screen. It works but it so lagging.
////            if(bullets.get(i).y<-100 ){
////                bullets.remove(i);
////                break;
////            }
//            bullets.get(i).update();
//            // this is trying to found the collision between bullets and rocks but it does not work
////            for(int n = 0; n < rocks.size(); i++){
////                if(collision(bullets.get(i), rocks.get(n))){
////                    bullets.remove(i);
////                    rocks.remove(n);
////                    break;}}
//
//
//
//            }


        for(int i = 0; i < rocks.size(); i++){
            rocks.get(i).update();
            // check the collision between rocks and the ship it work but a bit lag
            if(collision(rocks.get(i), spaceShip)){
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
            shoot(true);
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
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
//            System.out.print("x:");System.out.print(x);
//            System.out.print("  X:");System.out.println(spaceShip.x);
//            System.out.print("y:");System.out.print(y);
//            System.out.print("  Y:");System.out.println(spaceShip.y);
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
//            for(Bullet m:bullets){
//                m.draw(canvas);
//            }
            // update rock
            for(Rock r:rocks){
                r.draw(canvas);
            }
            canvas.restoreToCount(savedState);
            copySavestate = savedState;
        }
    }
}