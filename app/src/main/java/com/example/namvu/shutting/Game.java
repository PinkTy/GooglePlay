package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Game extends Activity {
    private GameSoundPool sounds;
    private menu Menu;
    private GamePanel mainGame;
    private choosStyle choosePanel;
    private store Store;
    public static int BULLET_STYLE1, BULLET_COlOR1, SPACESHIP_STYLE1, SHIP_COlOR1;

    public static final String SHARED_PREFS = "Game_data1";
    public static final String SCORE_STYLEONE = "Score_1";
    public static final String SCORE_STYLETWO = "Score_2";
    public static final String SCORE_STYLETHREE = "Score_3";
    public static final String SPACESHIP_STYLE = "SPACESHIP_STYLE";
    public static final String SHIP_COlOR = "SHIP_COlOR";
    public static final String BULLET_STYLE = "BULLET_STYLE";
    public static final String BULLET_COlOR = "BULLET_COlOR";
    public static int scoreGame1, scoreGame2, scoreGame3;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ConstantUtil.TO_GAME_PANEL) {
                toMainGame();
            }
            else if (msg.what == ConstantUtil.TO_CHOOSE_PANEL) {
                toChooseView();
            }
            else if (msg.what == ConstantUtil.TO_STORE_PANEL) {
                toStoreView();
            }
            else if (msg.what == ConstantUtil.TO_MENU_PANEL) {
                toMenuView();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Menu = new menu(this, sounds);
        setContentView(Menu);
//        Menu = new menu(this, sounds);
//        setContentView(Menu);
        //setContentView(new GamePanel(this, sounds));
    }
    private long firstTime = 0;
    public void saveData(int GameSave){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (GameSave){
            case 1:
                editor.putInt(SCORE_STYLEONE, GamePanel.scoregame1);
                break;
            case 2:
                editor.putInt(SCORE_STYLETWO,GamePanel.scoregame2);
                break;
            case 3:
                editor.putInt(SCORE_STYLETHREE,GamePanel.scoregame3);
                break;
            case 4:
                editor.putInt(SPACESHIP_STYLE,store.SPACESHIP_STYLE);
                editor.putInt(SHIP_COlOR,store.SHIP_COlOR);
                editor.putInt(BULLET_COlOR,store.BULLET_COlOR);
                editor.putInt(BULLET_STYLE,store.BULLET_STYLE);

                break;

        }
        editor.apply();
    }
    public void loadDataShip(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        store.SPACESHIP_STYLE = sharedPreferences.getInt(SPACESHIP_STYLE,1);
        store.SHIP_COlOR = sharedPreferences.getInt(SHIP_COlOR,1);
        store.BULLET_COlOR= sharedPreferences.getInt(BULLET_COlOR,1);
        store.BULLET_STYLE = sharedPreferences.getInt(BULLET_STYLE,1);
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        scoreGame1 = sharedPreferences.getInt(SCORE_STYLEONE,0);
        scoreGame2 = sharedPreferences.getInt(SCORE_STYLETWO,0);
        scoreGame3 = sharedPreferences.getInt(SCORE_STYLETHREE,0);
    }
    public void updateData(){
        GamePanel.highScoregame1 = scoreGame1;
        GamePanel.highScoregame2 = scoreGame2;
        GamePanel.highScoregame3 = scoreGame3;
    }

    public void toMainGame() {
        if (mainGame == null) {
            mainGame = new GamePanel(this, sounds);
        }
        setContentView(mainGame);
        Menu = null;
    }

    public void toStoreView() {
        if (Store == null) {
            Store = new store(this, sounds);
        }
        setContentView(Store);
        Menu = null;
    }


    public void toChooseView() {
        if (choosePanel == null) {
            choosePanel = new choosStyle(this, sounds);
        }
        setContentView(choosePanel);
        Menu = null;
    }

    public void toMenuView() {
        if (Menu == null) {
            Menu = new menu(this, sounds);
        }
        setContentView(Menu);
        Store = null;
    }


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (DebugConstant.DOUBLECLICK_EXIT) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    Toast.makeText(Game.this, "Double click to exist", Toast.LENGTH_SHORT).show();
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
