package com.example.namvu.shutting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


import java.util.HashMap;

public class GameSoundPool {
    private Game game;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> map;

    @SuppressLint("UseSparseArrays")
    public GameSoundPool(Game game) {
        this.game = game;
        map = new HashMap<Integer, Integer>();
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
    }

    public void initGameSound() {
        map.put(1, soundPool.load(game, R.raw.button, 1));
    }

    public void playSound(int sound, int loop) {
        AudioManager am = (AudioManager) game.getSystemService(Context.AUDIO_SERVICE);
        float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volume = stramVolumeCurrent / stramMaxVolumeCurrent;
        soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
    }
}
