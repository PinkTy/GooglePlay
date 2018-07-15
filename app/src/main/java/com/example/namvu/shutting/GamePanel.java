package com.example.namvu.shutting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.animation.ObjectAnimator;
import android.view.View;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


public class GamePanel extends BaseWindow implements SurfaceHolder.Callback {
    public Dictionary<Integer, Bullet> dictionaryBullets = new Hashtable<>();
    Integer countBullets = 0;

    private Random rand = new Random();
    public Rect rect, rectTryagain, rectBack;
    private long timeCount;
    private long delayBeforeStartLevel;
    private int timeToCreateRock;
    private int level = 0;
    private int firstTime = 1;
    Bitmap[] rockImage = new Bitmap[10];
    Bitmap[] explosionImageR = new Bitmap[9];
    Bitmap[] explosionImageRSmall = new Bitmap[9];
    Bitmap[] explosionImageSonic = new Bitmap[9];
    Bitmap[] explosionImageSonicSmall = new Bitmap[9];
    public static Bitmap[] NamAndYAngSpaceShip = new Bitmap[16];

    public static int copySavestate;
    public static float copyscaleFactorX;
    public static float copyscaleFactorY;
    public static int screenWIDTH;
    public static int screenHEIGHT;
    public static int scoregame1, scoregame2, scoregame3;
    public static int highScoregame1;
    public static int highScoregame2;
    public static int highScoregame3;
    public static int STYLE;
    public static int WIDTH;
    public static int HEIGHT;
    private boolean shoot, delete;
    public static boolean gameOver = false;
    private MainThread thread;
    private Background bg;
    private long bulletStartTime;
    public long timeDelayShoot = 200;
    private SpaceShip spaceShip;
    private Bitmap gameover, highscore, tryagain, back;
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public static ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    public static ArrayList<Rock> rocks = new ArrayList<Rock>();
    private MediaPlayer mMediaPlayer;
    private MediaPlayer backsound;
    public Paint paint4;
    private MediaPlayer shootSound;
    private MediaPlayer explosionSound;


    public GamePanel(Context context, GameSoundPool sounds) {
        super(context, sounds);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        //make gamePanel focusable so it can handle events
        setFocusable(true);
        game.loadDataShip();
        //music
        backsound = MediaPlayer.create(game, R.raw.button);
        backsound.setLooping(false);
        shootSound = MediaPlayer.create(game, R.raw.shoot);
        shootSound.setLooping(false);
        explosionSound = MediaPlayer.create(game, R.raw.shoot);
        explosionSound.setLooping(false);
        mMediaPlayer = MediaPlayer.create(game, R.raw.testmusic);
        mMediaPlayer.setLooping(true);
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    public Rect getRectangle(int x, int y, int width, int height) {
        return new Rect(x, y, x + width, y + height);
    }

    public boolean shoot(boolean shot) {
        return shoot = shot;
    }

    public void savedata() {


    }

    // this function create a new Rock. the fall from the top of the screen
    public void newRock(Bitmap[] rockimages) {

        int i = 0 + rand.nextInt(10 - 1);
        rocks.add(new Rock(rockimages[i], spaceShip.timeChange));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        bg.setVector(5);
        back = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        timeCount = System.nanoTime();
        WIDTH = bg.image.getWidth();
        HEIGHT = bg.image.getHeight();
        rect = new Rect(0, 0, WIDTH-20, HEIGHT);
        copyscaleFactorY = getHeight() / (HEIGHT * 1.f);
        copyscaleFactorX = getWidth() / (WIDTH * 1.f);
        paint.setTextSize(40);
        switch (store.SPACESHIP_STYLE){
            case 1:
                switch (store.SHIP_COlOR){
                    case 1:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership1blue), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 2:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership1red), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 3:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership1green), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 4:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership1orange), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                }
                break;
            case 2:
                switch (store.SHIP_COlOR){
                    case 1:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership2blue), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 2:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership2red), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 3:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership2green), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 4:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership2orange), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                }
                break;
            case 3:
                switch (store.SHIP_COlOR){
                    case 1:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.spaceship), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 2:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership3red), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 3:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership3green), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 4:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.playership3orange), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                }
                break;
            case 4:
                switch (store.SHIP_COlOR){
                    case 1:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.ufoblue), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 2:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.ufored), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 3:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.ufogreen), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                    case 4:
                        spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.ufoyellow), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                        break;
                }
                break;
            case 5:
                NamAndYAngSpaceShip[0] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1blue);
                NamAndYAngSpaceShip[1] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1red);
                NamAndYAngSpaceShip[2] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1green);
                NamAndYAngSpaceShip[3] =BitmapFactory.decodeResource(getResources(), R.drawable.playership1orange);
                NamAndYAngSpaceShip[4] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2blue);
                NamAndYAngSpaceShip[5] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2red);
                NamAndYAngSpaceShip[6] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2green);
                NamAndYAngSpaceShip[7] =BitmapFactory.decodeResource(getResources(), R.drawable.playership2orange);
                NamAndYAngSpaceShip[8] =BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
                NamAndYAngSpaceShip[9] =BitmapFactory.decodeResource(getResources(), R.drawable.playership3red);
                NamAndYAngSpaceShip[10] =BitmapFactory.decodeResource(getResources(), R.drawable.playership3green);
                NamAndYAngSpaceShip[11] =BitmapFactory.decodeResource(getResources(), R.drawable.playership3orange);
                NamAndYAngSpaceShip[12] =BitmapFactory.decodeResource(getResources(), R.drawable.ufoblue);
                NamAndYAngSpaceShip[13] =BitmapFactory.decodeResource(getResources(), R.drawable.ufored);
                NamAndYAngSpaceShip[14] =BitmapFactory.decodeResource(getResources(), R.drawable.ufogreen);
                NamAndYAngSpaceShip[15] =BitmapFactory.decodeResource(getResources(), R.drawable.ufoyellow);
                spaceShip = new SpaceShip(BitmapFactory.decodeResource(getResources(), R.drawable.ufoyellow), BitmapFactory.decodeResource(getResources(), R.drawable.shield));
                break;



        }
        bulletStartTime = System.nanoTime();
        // load bitmap of gameover
        gameover = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
        gameover = Bitmap.createScaledBitmap(gameover, gameover.getWidth() / 2, gameover.getHeight() / 2, false);
        highscore = BitmapFactory.decodeResource(getResources(), R.drawable.highscore);
        highscore = Bitmap.createScaledBitmap(highscore, highscore.getWidth() / 3, highscore.getHeight() / 3, false);
        tryagain = BitmapFactory.decodeResource(getResources(), R.drawable.tryagain);
        tryagain = Bitmap.createScaledBitmap(tryagain, tryagain.getWidth() / 3, tryagain.getHeight() / 3, false);
        // load rock bitmap

        rockImage[0] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownbigfour);
        rockImage[1] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownbigone);
        rockImage[2] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownbigtwo);
        rockImage[3] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownbigthree);
        rockImage[4] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownmedone);
        rockImage[5] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownmedtwo);
        rockImage[6] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownsmallone);
        rockImage[7] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrownsmalltwo);
        rockImage[8] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrowntinyone);
        rockImage[9] = BitmapFactory.decodeResource(getResources(), R.drawable.meteorbrowntinytwo);

        // load Regular Explosion
        explosionImageR[0] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion00);
        explosionImageR[1] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion01);
        explosionImageR[2] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion02);
        explosionImageR[3] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion03);
        explosionImageR[4] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion04);
        explosionImageR[5] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion05);
        explosionImageR[6] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion06);
        explosionImageR[7] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion07);
        explosionImageR[8] = BitmapFactory.decodeResource(getResources(), R.drawable.regularexplosion08);
        for (int i = 0; i < explosionImageR.length; i++) {
            int w = explosionImageR[i].getWidth() / 3;
            int h = explosionImageR[i].getHeight() / 3;
            explosionImageRSmall[i] = Bitmap.createScaledBitmap(explosionImageR[i], w, h, false);
        }


        // load Sonic Explosion
        explosionImageSonic[0] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion00);
        explosionImageSonic[1] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion01);
        explosionImageSonic[2] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion02);
        explosionImageSonic[3] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion03);
        explosionImageSonic[4] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion04);
        explosionImageSonic[5] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion05);
        explosionImageSonic[6] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion06);
        explosionImageSonic[7] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion07);
        explosionImageSonic[8] = BitmapFactory.decodeResource(getResources(), R.drawable.sonicexplosion08);
        for (int i = 0; i < explosionImageSonic.length; i++) {
            int w = explosionImageSonic[i].getWidth() / 2;
            int h = explosionImageSonic[i].getHeight() / 2;
            explosionImageSonicSmall[i] = Bitmap.createScaledBitmap(explosionImageSonic[i], w, h, false);
        }

        // create 50 rocks at the start of the game
        switch (STYLE) {
            //create rock for avoid Rock game
            case 1: {
                timeToCreateRock = 10000;
                spaceShip.timeShutting = -999999999;
                for (int n = 0; n < 7; n++) {
                    newRock(rockImage);

                }
                break;
            }
            // create rcck for shutting game
            case 2: {
                timeToCreateRock = 100;
                for (int n = 0; n < 25; n++) {
                    newRock(rockImage);
                }
                break;
            }
            // create the rock for level game

        }


        //we can safely start the game loop
        thread.setRunning(true);
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            shoot = false;
            level = 0;
            gameOver = false;
            thread.start();
        }
//        if (thread.getState() == Thread.State.NEW)
//        {
//            thread.setRunning(true);
//            thread.start();
//        }
    }


    public void update() {
        spaceShip.update();
        bg.update();
        // This is using for update the bullet
        // counting time delay for shoot

        long elapsed = (System.nanoTime() - bulletStartTime) / 1000000;
        if (shoot && elapsed > (timeDelayShoot - spaceShip.timeShutting)) {
            shootSound.start();
            switch (store.BULLET_STYLE){
                case 1:
                    switch (store.BULLET_COlOR){
                        case 1:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 2:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;

                            case 3:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 4:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen1), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                    }
                    break;
                case 2:
                    switch (store.BULLET_COlOR){
                        case 1:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, -30));
                                countBullets += 1;
                            }
                            break;
                            case 2:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 3:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, -30));
                                countBullets += 1;
                            }
                            break;

                        case 4:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue2), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 50, -40, -30));
                                countBullets += 1;
                            }
                            break;
                    }

                    break;
                case 3:
                    switch (store.BULLET_COlOR) {
                        case 1:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 2:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 3:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 4:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue3), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height + 10, -40, -30));
                                countBullets += 1;
                            }
                            break;
                    }

                case 4:
                    switch (store.BULLET_COlOR) {
                        case 1:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 2:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 3:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, -30));
                                countBullets += 1;
                            }
                            break;
                        case 4:
                            dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserred4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 0));
                            countBullets += 1;
                            if (spaceShip.enableBullet2) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.lasergreen4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, 30));
                                countBullets += 1;
                            }
                            if (spaceShip.enableBullet3) {
                                dictionaryBullets.put(countBullets, new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.laserblue4), spaceShip.rect.centerX() - 9, spaceShip.rect.top - spaceShip.height +50, -40, -30));
                                countBullets += 1;
                            }
                            break;

                    }


            }


            bulletStartTime = System.nanoTime();


        }

        for (Enumeration e = dictionaryBullets.keys(); e.hasMoreElements(); ) {
            Object object = e.nextElement();
            delete = false;
            for (int n = 0; n < rocks.size(); n++) {

                if (collision(rocks.get(n), dictionaryBullets.get(object))) {
                    explosionSound.start();
                    if (!gameOver) {
                        spaceShip.score += rocks.get(n).width;
                    }
                    explosions.add(new Explosion(explosionImageRSmall, rocks.get(n).rectangle.centerX(), rocks.get(n).rectangle.centerY()));
                    delete = true;
                    dictionaryBullets.remove(object);
                    rocks.remove(n);
                    switch (STYLE) {

                        case 1: {

                            if (rocks.size() < 25) {
                                newRock(rockImage);
                            }
                            break;
                        }
                        case 2: {
                            if (rocks.size() < 150) {
                                newRock(rockImage);
                            }
                            break;

                        }


                    }
                    break;
                }
            }

            if (!delete) {


                if (dictionaryBullets.get(object).y < -100) {
                    dictionaryBullets.remove(object);


                } else {

                    dictionaryBullets.get(object).update();
                }

            }

        }


        for (int i = 0; i < rocks.size(); i++) {
            rocks.get(i).update();
            // check the collision between rocks and the ship it work but a bit lag
            if (collision(rocks.get(i), spaceShip)) {
                explosionSound.start();
                spaceShip.health -= rocks.get(i).width / 2;
                if (spaceShip.health < 0) {
                    explosions.add(new Explosion(explosionImageSonicSmall, spaceShip.x - 60, spaceShip.rect.top - 90));
                    spaceShip.health = 0;
                    spaceShip.moving(false);
                    if(!((!rect.contains(spaceShip.x,spaceShip.y))&&spaceShip.rect.centerY() <50)){
                        spaceShip.setY(-500);
                        gameOver = true;
                        spaceShip.moving(false);
                    }


                }
                if (!gameOver) {
                    spaceShip.score += rocks.get(i).width;
                }
                explosions.add(new Explosion(explosionImageRSmall, rocks.get(i).rectangle.centerX(), rocks.get(i).rectangle.centerY()));
                rocks.remove(i);
                switch (STYLE) {

                    case 1: {

                        if (rocks.size() < 25) {
                            newRock(rockImage);
                        }
                        break;
                    }
                    case 2: {
                        if (rocks.size() < 150) {
                            newRock(rockImage);
                        }
                        break;

                    }


                }
//                layer.setPlaying(false);


            }
//            for(int n = 0; n < bullets.size(); i++){
//                if(collision(bullets.get(n), rocks.get(i))){
//                    bullets.remove(n);
//                    rocks.remove(i);}}
//
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).remove) {
                explosions.remove(i);
            }
        }

        //Increase the number of rock
        switch (STYLE) {

            case 1: {
                long elapsedTime = (System.nanoTime() - timeCount) / 1000000;
                if (elapsedTime > timeToCreateRock && rocks.size() < 25) {
                    newRock(rockImage);
                    timeCount = System.nanoTime();
                }
                break;
            }
            case 2: {
                long elapsedTime = (System.nanoTime() - timeCount) / 1000000;
                if (elapsedTime > timeToCreateRock && rocks.size() < 150) {
                    newRock(rockImage);
                    timeCount = System.nanoTime();
                }
                break;

            }


        }
        if (gameOver) {
            for (int i = 0; i < rocks.size(); i++) {
                rocks.remove(i);
            }
        }

    }

    // collision function. that check the collision between 2 object of GameObject
    public boolean collision(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getRawX() / copyscaleFactorX);
            int y = (int) (event.getRawY() / copyscaleFactorY);
            shoot(true);
            if (spaceShip.rectFortouch.contains(x, y) && !gameOver) {
                spaceShip.setXY(x, y);
                spaceShip.moving(true);
            }
            // back
            if (gameOver &&rectBack.contains(x, y)){
                backsound.start();
                thread.setRunning(false);
                game.getHandler().sendEmptyMessage(ConstantUtil.TO_MENU_PANEL);

            }
            // retry the game
            if (gameOver && rectTryagain.contains(x, y) && rocks.size() == 0) {
                backsound.start();
                level = 0;
                spaceShip.timeChange = 0;
                spaceShip.scoreForStyleOne = 0;
                spaceShip.score = 0;
                spaceShip.x = WIDTH / 2;
                spaceShip.y = HEIGHT - spaceShip.height - 200;
                spaceShip.health = 100;
                gameOver = false;
                switch (STYLE) {
                    //create rock for avoid Rock game
                    case 1: {
                        timeToCreateRock = 10000;
                        spaceShip.timeShutting = -999999999;
                        for (int n = 0; n < 7; n++) {
                            newRock(rockImage);

                        }
                        break;
                    }
                    // create rcck for shutting game
                    case 2: {
                        timeToCreateRock = 100;
                        for (int n = 0; n < 25; n++) {
                            newRock(rockImage);
                        }
                        break;
                    }

                    // create the rock for level game

                }


            }

            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE && spaceShip.move&&!gameOver) {
            int x = (int) (event.getRawX() / copyscaleFactorX);
            int y = (int) (event.getRawY() / copyscaleFactorY);
            if (x + spaceShip.width > WIDTH) {
                x = WIDTH - spaceShip.width;
            }
            if (y > HEIGHT - spaceShip.height) {
                y = HEIGHT - spaceShip.height;
            }
            if (spaceShip.rectFortouch.contains(x, y)) {
                spaceShip.moving(true);
            }
            spaceShip.setXY(x, y);
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            shoot(false);
            spaceShip.moving(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void drawText(Canvas canvas, int shipHealth) {
        Paint paint = new Paint();

        float green = (shipHealth * 1.f / 100 * 1.f) * 225;
        float red = (1 - (shipHealth * 1.f / 100 * 1.f)) * 225;
        int barLength = (int) ((shipHealth * 1.f / 100 * 1.f) * 200);
//        if (green < 0) green =0;
//        if (red > 225) red =225;
//        if (barLength < 0) barLength =0;

        paint.setColor(Color.rgb((int) red, (int) green, 0));
        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setTextSize(40);
        paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        if (!gameOver) {
            if (STYLE == 1) {
                scoregame1 = spaceShip.scoreForStyleOne;
                canvas.drawText("" + spaceShip.scoreForStyleOne, WIDTH / 2 - 50, 40, paint1);
            } else if (STYLE == 2) {
                scoregame2 = spaceShip.score;
                canvas.drawText("" + spaceShip.score, WIDTH / 2 - 50, 40, paint1);
            } else {
                scoregame3 = spaceShip.score;
                canvas.drawText("" + spaceShip.score, WIDTH / 2 - 50, 40, paint1);
            }
        }


        Paint paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setTextSize(120);
        paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));


        int barHeight = 10;
        float fill = (shipHealth * 1.f / 100 * 1.f) * (barLength * 1.f);
        Rect barHealth = new Rect(5, 5, 5 + barLength, barHeight);

        canvas.drawRect(barHealth, paint);
        canvas.drawLine(5, 4, 5 + 200 + 1, 4, paint);
        canvas.drawLine(5, barHeight, 5 + 200 + 1, barHeight, paint);
        canvas.drawLine(5, 4, 5, barHeight, paint);
        canvas.drawLine(5 + 200 + 1, barHeight, 5 + 200 + 1, 4, paint);
        // create GameOver box
        if (gameOver) {
            game.loadData();

            game.updateData();
//            System.out.println("highScoregame1;" +highScoregame1);
//            System.out.println("highScoregame2;" +highScoregame2);
//            System.out.println("highScoregame3;" +highScoregame3);
            canvas.drawBitmap(gameover, WIDTH / 2 - 280, HEIGHT / 2 - 400, null);
            canvas.drawBitmap(highscore, WIDTH / 2 - 250, HEIGHT / 2, null);
            canvas.drawBitmap(back, WIDTH / 2 - back.getWidth() / 2, HEIGHT / 2 + 500, paint4);
            rectBack = new Rect(WIDTH / 2 - back.getWidth() / 2, HEIGHT / 2 + 500, WIDTH / 2 - back.getWidth() / 2 + back.getWidth(), HEIGHT / 2 + 500 + back.getHeight());
            canvas.drawBitmap(tryagain, WIDTH / 2 - 250, HEIGHT / 2 + 300, null);
            rectTryagain = new Rect(WIDTH / 2 - 250, HEIGHT / 2 + 300, WIDTH / 2 - 250 + tryagain.getWidth(), HEIGHT / 2 + 300 + tryagain.getHeight());
//            canvas.drawRect(rectTryagain, );

            if (STYLE == 1) {
                if (spaceShip.scoreForStyleOne > highScoregame1) {
                    highScoregame1 = spaceShip.scoreForStyleOne;
//                    System.out.println("highScoregame1saved;" +highScoregame1);
                    game.saveData(1);
                }
                canvas.drawText("" + highScoregame1, WIDTH / 2 - 230, HEIGHT / 2 + 200, paint2);
                canvas.drawText("" + spaceShip.scoreForStyleOne, WIDTH / 2 - 230, HEIGHT / 2 - 100, paint2);

            } else if (STYLE == 2) {
                if (spaceShip.score > highScoregame2) {
                    highScoregame2 = spaceShip.score;
                    game.saveData(2);
                }
                canvas.drawText("" + highScoregame2, WIDTH / 2 - 230, HEIGHT / 2 + 200, paint2);
                canvas.drawText("" + spaceShip.score, WIDTH / 2 - 250, HEIGHT / 2 - 100, paint2);
            } else {
                if (spaceShip.score > highScoregame3) {
                    highScoregame3 = spaceShip.score;
                    game.saveData(3);
                }
                canvas.drawText("" + highScoregame3, WIDTH / 2 - 230, HEIGHT / 2 + 200, paint2);
                canvas.drawText("" + spaceShip.score, WIDTH / 2 - 250, HEIGHT / 2 - 100, paint2);
            }

//            canvas.drawRect(rectBack,paint1);
        }


        // 10 levels for game style 3
        if (STYLE == 3) {
            if (rocks.size() == 0 && !gameOver) {
                switch (level) {
                    case 0: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 100; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    //create rock for avoid Rock game
                    case 1: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 200; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    // create rcck for shutting game
                    case 2: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 300; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    // create the rock for level game
                    case 3: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 400; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 4: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 500; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 5: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 600; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 6: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 700; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 7: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 800; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 8: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 900; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 9: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 1000; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);
                        }
                        break;
                    }
                    case 10: {
                        if (firstTime == 1) {
                            delayBeforeStartLevel = System.nanoTime();
                            firstTime += 1;
                        }
                        long resetLevel = (System.nanoTime() - delayBeforeStartLevel) / 1000000;
                        if (resetLevel > 2500) {
                            for (int n = 0; n < 1500; n++) {
                                newRock(rockImage);
                            }
                            level += 1;
                            firstTime = 1;
                        } else {
                            canvas.drawText("Level " + (level + 1), WIDTH / 2 - 175, HEIGHT / 2, paint2);

                        }
                        break;
                    }
                    case 11:{

                        canvas.drawText("I hope someone will be at this stage" , WIDTH / 2 - 275, HEIGHT / 2 -100, paint1);
                        canvas.drawText("Congratulations!!!! you defeat my game!! I hope you enjoy the easter egg" , 200, HEIGHT / 2, paint1);
                        canvas.drawText("Can you please send an email to:vuphuongnam81197@gmail.com with the state picture" , 20, HEIGHT / 2 +100, paint1);
                        canvas.drawText("Can you please send an email to:dylanaa114@gmail.com with the state picture" , 100, HEIGHT / 2 +200, paint1);
                        canvas.drawText("You are sucks. Did you find invisible SpaceShip? !!!!!!!!!!!!By Yang!!!!!!" , 200, HEIGHT / 2 +300, paint1);
                        canvas.drawText("It will make my day!!!" , WIDTH / 2 - 175, HEIGHT / 2 +400, paint1);

                    }
                }
            }


        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
//        System.out.println(getWidth());
//        System.out.println(getHeight());
//        System.out.println(scaleFactorX);
//        System.out.println(scaleFactorY);
//
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            //System.out.println((!rect.contains(spaceShip.x,spaceShip.y))&&spaceShip.rect.centerY() <50);
            spaceShip.draw(canvas);
            for (Enumeration e = dictionaryBullets.keys(); e.hasMoreElements(); ) {
                dictionaryBullets.get(e.nextElement()).draw(canvas);
            }

            // update rock
            for (Rock r : rocks) {
                r.draw(canvas);
            }
            for (Explosion e : explosions) {
                e.draw(canvas);
            }
            drawText(canvas, spaceShip.health);
            canvas.restoreToCount(savedState);
            copySavestate = savedState;
        }
    }
}