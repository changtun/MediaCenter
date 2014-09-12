/*
 * File Name:  MusicPlayerService.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-9
 * Changed Content:  <Changed Content>
 */
package com.pbi.center.service;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.IBinder;

import com.pbi.center.domain.AudioInfo;
import com.pbi.center.global.Config;
import com.pbi.center.utils.LogUtil;
import com.pbi.center.utils.MediaUtil;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-9]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class MusicPlayerService extends Service
{
    private static final String TAG = "MusicPlayerService";
    
    private static final int MSG_UPDATE_PROGRESS = 0;
    
    private MediaPlayer player;
    
    private List<AudioInfo> listSongs;
    
    private String path;
    
    private int pos;
    
    private int progress;/*播放进度*/
    private int duration;/*文件时长*/
    private int current;/*当前播放时长*/
    private boolean isPause;
    
    
    private Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case MSG_UPDATE_PROGRESS:
                    if (null != player)
                    {
                        current = player.getCurrentPosition();
                        if(duration != 0)
                        {
                            progress = (int)((current / (double)duration) * 100); // 获取当前音乐播放的位置
                        }
                        
                        Intent intent = new Intent();
                        intent.setAction(Config.ACTION.ACTION_UPDATE_PROGRESS);
                        intent.putExtra("progress", progress);
                        intent.putExtra("current", MediaUtil.formatTime(current));
                        sendBroadcast(intent);
                        
                        handler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 1500);
                    }
                    break;
                
                case Config.MSG.MSG_MUSIC_PLAY:
                    play(0);
                    break;
                
                case Config.MSG.MSG_MUSIC_PAUSE:
                    pause();
                    break;
                
                case Config.MSG.MSG_MUSIC_RESUME:
                    resume();
                    break;
                
                case Config.MSG.MSG_MUSIC_PREVIOUS:
                    previous();
                    break;
                
                case Config.MSG.MSG_MUSIC_NEXT:
                    next();
                    break;
                
                case Config.MSG.MSG_MUSIC_SEEK:
                    current = (int)((double)duration * progress / 100);
                    play(current);
                    break;
            }
        };
    };
    
    /**
     * Override Method
     * 
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        player = new MediaPlayer();
        listSongs = MediaUtil.getAudioInfos(this);
        player.setOnCompletionListener(new OnCompletionListener()
        {
            
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                // TODO: 设置播放模式
            }
        });
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (null == intent)
        {
            LogUtil.show(Config.LOG.ERROR, TAG, " intent is null,start service failed...");
            return -1;
        }
        
        int msgType = intent.getIntExtra("msg", -1);
        switch (msgType)
        {
            case Config.MSG.MSG_MUSIC_PLAY:
                path = intent.getStringExtra("path");
                pos = intent.getIntExtra("pos", 0);
                handler.removeMessages(msgType);
                handler.sendEmptyMessage(msgType);
                break;
            
            case Config.MSG.MSG_MUSIC_PAUSE:
                handler.removeMessages(msgType);
                handler.sendEmptyMessage(msgType);
                break;
            
            case Config.MSG.MSG_MUSIC_RESUME:
                handler.removeMessages(msgType);
                handler.sendEmptyMessage(msgType);
                break;
            
            case Config.MSG.MSG_MUSIC_PREVIOUS:
                handler.removeMessages(msgType);
                handler.sendEmptyMessage(msgType);
                break;
            
            case Config.MSG.MSG_MUSIC_NEXT:
                handler.removeMessages(msgType);
                handler.sendEmptyMessage(msgType);
                break;
            
            case Config.MSG.MSG_MUSIC_SEEK:
                progress = intent.getIntExtra("progress", 0);
                handler.removeMessages(msgType);
                handler.removeMessages(MSG_UPDATE_PROGRESS);
                handler.sendEmptyMessage(msgType);
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    
    /**
     * 
     * <Functional overview> <Functional Details>
     * 
     * @param currentTime 歌曲当前播放时长
     * @see [Class, Class#Method, Class#Member]
     */
    private void play(int currentTime)
    {
        try
        {
            player.reset();// 把各项参数恢复到初始状态
            player.setDataSource(path);
            player.prepare(); // 进行缓冲
            player.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
            handler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void pause()
    {
        if (null != player && player.isPlaying())
        {
            player.pause();
            isPause = true;
        }
    }
    
    private void resume()
    {
        if (isPause)
        {
            player.start();
            isPause = false;
        }
    }
    
    private void stop()
    {
        
    }
    
    private void previous()
    {
        
    }
    
    private void next()
    {
        
    }
    
    private final class PreparedListener implements OnPreparedListener
    {
        private int currentTime;
        
        public PreparedListener(int currentTime)
        {
            this.currentTime = currentTime;
        }
        
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            player.start();
            if (currentTime > 0)
            {
                // 如果音乐不是从头播放
                player.seekTo(currentTime);
            }
            
            Intent intent = new Intent();
            intent.setAction(Config.ACTION.ACTION_UPDATE_DURATION);
            duration = player.getDuration();
            intent.putExtra("duration", MediaUtil.formatTime(duration)); // 通过Intent来传递歌曲的总长度
            sendBroadcast(intent);
        }
    }
    
}
