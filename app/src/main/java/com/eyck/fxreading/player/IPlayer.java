package com.eyck.fxreading.player;

/**
 * Created by Eyck on 2017/9/13.
 */

public interface IPlayer {

    boolean play();

    boolean play(String song);

    boolean pause();

    boolean isPlaying();

    int getProgress();

    int getDuration();

    boolean seekTo(int progress);

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    interface Callback {

        void onComplete(PlayState state);

        void onPlayStatusChanged(PlayState status);

        void onPosition(int position);
    }
}
