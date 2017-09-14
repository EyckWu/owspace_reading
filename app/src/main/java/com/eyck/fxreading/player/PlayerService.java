package com.eyck.fxreading.player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.eyck.fxreading.R;
import com.eyck.fxreading.model.entity.Constant;
import com.eyck.fxreading.view.activity.MainActivity;
import com.orhanobut.logger.Logger;


/**
 * Created by Eyck on 2017/9/13.
 */

public class PlayerService extends Service implements IPlayer,IPlayer.Callback {


    private IBinder mBinder = new LocalBinder();
    private Player mPlayer;
    private RemoteViews mContentViewSmall;
    private RemoteViews mContentViewBig;


    public class LocalBinder extends Binder{
        public PlayerService getService(){
            return PlayerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Logger.d("onCreate");
        super.onCreate();
        mPlayer = Player.getInstance();
        mPlayer.registerCallback(this);
    }

    public String getSong() {
        return mPlayer.getSong();
    }

    public void setSong(String song) {
        mPlayer.setSong(song);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        if(intent != null) {
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_PLAY_TOGGLE)) {
                if(isPlaying()) {
                    pause();
                }else {
                    play();
                }
            }else if(action.equals(Constant.ACTION_STOP_SERVICE)) {
                if(isPlaying()) {
                    pause();
                }
                stopForeground(true);
                unregisterCallback(this);
            }
        }
        return START_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {
        Logger.d("stopService");
        stopForeground(true);
        unregisterCallback(this);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        Logger.d("onDestroy");
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public boolean play() {
        return mPlayer.play();
    }

    @Override
    public boolean play(String song) {
        return mPlayer.play(song);
    }

    @Override
    public boolean pause() {
        return mPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getProgress();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public boolean seekTo(int progress) {
        return mPlayer.seekTo(progress);
    }

    @Override
    public void registerCallback(Callback callback) {
        mPlayer.registerCallback(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mPlayer.unregisterCallback(callback);
    }

    @Override
    public void removeCallbacks() {
        mPlayer.removeCallbacks();
    }

    @Override
    public void releasePlayer() {
        mPlayer.releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onComplete(PlayState state) {
        showNotification(state);
    }

    @Override
    public void onPlayStatusChanged(PlayState status) {
        showNotification(status);
    }

    @Override
    public void onPosition(int position) {

    }

    private void showNotification(PlayState state) {
        PendingIntent intent = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.icon)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(intent)
                .setCustomContentView(getSmallContent())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .build();
        startForeground(Constant.NOTIFICATION_ID,notification);

    }

    private RemoteViews getSmallContent() {
        if(mContentViewSmall == null) {
            mContentViewSmall = new RemoteViews(getPackageName(),R.layout.remote_view_music_player_small);
            setUpRemoteView(mContentViewSmall);
        }
        updateRemoteViews(mContentViewSmall);
        return mContentViewSmall;
    }

    private RemoteViews getBigContentView() {
        if (mContentViewBig == null) {
            mContentViewBig = new RemoteViews(getPackageName(), R.layout.remote_view_music_player);
            setUpRemoteView(mContentViewBig);
        }
        updateRemoteViews(mContentViewBig);
        return mContentViewBig;
    }

    private void updateRemoteViews(RemoteViews mContentViewSmall) {

    }

    private void setUpRemoteView(RemoteViews remoteView) {
        remoteView.setImageViewResource(R.id.image_view_close, R.drawable.ic_remote_view_close);
        remoteView.setImageViewResource(R.id.image_view_play_last, R.drawable.ic_remote_view_play_last);
        remoteView.setImageViewResource(R.id.image_view_play_next, R.drawable.ic_remote_view_play_next);

        remoteView.setOnClickPendingIntent(R.id.button_close, getPendingIntent(Constant.ACTION_STOP_SERVICE));
        remoteView.setOnClickPendingIntent(R.id.button_play_last, getPendingIntent(Constant.ACTION_PLAY_LAST));
        remoteView.setOnClickPendingIntent(R.id.button_play_next, getPendingIntent(Constant.ACTION_PLAY_NEXT));
        remoteView.setOnClickPendingIntent(R.id.button_play_toggle, getPendingIntent(Constant.ACTION_PLAY_TOGGLE));
    }

    private PendingIntent getPendingIntent(String actionStopService) {
        return PendingIntent.getService(this,0,new Intent(actionStopService),0);
    }
}
