/*
 * File Name:  VideoPlayer.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-3
 * Changed Content:  <Changed Content>
 */
package com.pbi.center;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-3]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class VideoPlayer extends Activity
{
    EditText et_file_name;
    
    SurfaceView sv;
    
    SurfaceHolder holder;
    
    MediaPlayer mediaplayer;
    
    File file;
    
    boolean ispause;
    
    int position;
    
    ProgressBar pb;
    
    String urlpath;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 指定 界面没有title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mediaplayer = new MediaPlayer();
        pb = (ProgressBar)this.findViewById(R.id.pb);
        et_file_name = (EditText)this.findViewById(R.id.et_file_path);
        sv = (SurfaceView)this.findViewById(R.id.sv);
        holder = sv.getHolder();
        /* 下面设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到用户面前 */
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setKeepScreenOn(true);
        holder.addCallback(new Callback()
        {
            
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                System.out.println("destroyed ");
                // 停止视频 记录当前位置
                position = mediaplayer.getCurrentPosition();
                mediaplayer.pause();
            }
            
            public void surfaceCreated(SurfaceHolder holder)
            {
                System.out.println("create ");
                // 根据当前位置继续播放视频
                VideoPlayer.this.holder = holder;
                if (file != null && position > 0)
                    play(position);
            }
            
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });
        
        TimerTask task = new TimerTask()
        {
            
            @Override
            public void run()
            {
                if (mediaplayer != null && mediaplayer.isPlaying())
                {
                    int current = mediaplayer.getCurrentPosition();
                    pb.setProgress(current);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 1000);
    }
    
    public void click(View view)
    {
        String path = et_file_name.getText().toString();
        file = new File(path);
        if (path.contains("http://"))
        {
            urlpath = path;
        }
        play(0);
        
        // if(path.contains("http://")){
        // urlpath = path;
        // }
        // if (file!=null&&file.exists()) {
        // switch (view.getId()) {
        // case R.id.bt_play:
        // play(0);
        // break;
        // case R.id.bt_pause:
        // if (mediaplayer.isPlaying()) {
        // mediaplayer.pause();
        // ispause = true;
        // } else {
        // if (ispause) {
        // mediaplayer.start();
        // }
        // }
        // break;
        // case R.id.bt_replay:
        // if (mediaplayer.isPlaying()) {
        // mediaplayer.seekTo(0);
        // } else {
        // if (file != null) {
        // play(0);
        // }
        // }
        // break;
        // case R.id.bt_stop:
        // if (mediaplayer.isPlaying()) {
        // mediaplayer.stop();
        // }
        // break;
        //
        // }
        // } else {
        // Toast.makeText(this, "文件不存在", 1).show();
        // }
    }
    
    private void play(int position)
    {
        try
        {
            mediaplayer.reset();
            if (urlpath == null)
            {
                mediaplayer.setDataSource(file.getAbsolutePath());
            }
            else
            {
                mediaplayer.setDataSource(urlpath);
            }
            mediaplayer.setDisplay(holder);
            mediaplayer.setOnPreparedListener(new OnVideoPrepared(position));
            mediaplayer.prepareAsync();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "文件格式不支持", 1).show();
        }
        
    }
    
    private class OnVideoPrepared implements OnPreparedListener
    {
        int position;
        
        public OnVideoPrepared(int position)
        {
            this.position = position;
        }
        
        public void onPrepared(MediaPlayer mp)
        {
            pb.setMax(mp.getDuration());
            if (position > 0)
            {
                mp.start();
                mp.seekTo(position);
            }
            else
            {
                mp.start();
            }
        }
    }
}
