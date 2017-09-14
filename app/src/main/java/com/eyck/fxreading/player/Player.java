package com.eyck.fxreading.player;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyck on 2017/9/13.
 */

public class Player implements IPlayer,MediaPlayer.OnCompletionListener,MediaPlayer.OnBufferingUpdateListener ,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener{

    private static volatile Player mInstance;
    private MediaPlayer mPlayer;
    private List<Callback> mCallbacks = new ArrayList<>();
    private boolean isPaused;

    private String song;

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Player() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
    }

    public static Player getInstance() {
        if (mInstance == null) {
            synchronized (Player.class) {
                if (mInstance == null) {
                    mInstance = new Player();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlayer.reset();
        notifyStatusChanged(PlayState.COMPLETE);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
        notifyStatusChanged(PlayState.PLAYING);
    }

    @Override
    public boolean play() {
        if(isPaused) {
            mPlayer.start();
            notifyStatusChanged(PlayState.PLAYING);
            return true;
        }
        return false;
    }

    @Override
    public boolean play(String song) {
        try {
            mPlayer.reset();
            mPlayer.setDataSource(song);
            mPlayer.prepare();
            this.song = song;
            notifyStatusChanged(PlayState.PLAYING);
            return true;
        } catch (IOException e) {
            notifyStatusChanged(PlayState.ERROR);
            e.printStackTrace();
        }
        return false;
    }

    private void notifyStatusChanged(PlayState state) {
        for (Callback c:mCallbacks){
            c.onPlayStatusChanged(state);
        }
    }

    @Override
    public boolean pause() {
        if(mPlayer.isPlaying()) {
            mPlayer.pause();
            isPaused = true;
            notifyStatusChanged(PlayState.PAUSE);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public boolean seekTo(int progress) {
        try{
            mPlayer.seekTo(progress);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void registerCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
        mCallbacks.clear();
    }

    @Override
    public void releasePlayer() {
        mPlayer.reset();
        mPlayer.release();;
        mPlayer = null;
        mInstance = null;
        song = null;
    }
}
