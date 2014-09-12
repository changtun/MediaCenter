/*
 * File Name:  MainActivity.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-8-29
 * Changed Content:  <Changed Content>
 */
package com.pbi.center;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.pbi.center.view.CustomImageView;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-8-29]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class MainActivity extends Activity implements CustomImageView.OnViewClickListener
{
    private static final int SKIP_MUSIC = 100;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        CustomImageView civPic = (CustomImageView)this.findViewById(R.id.civ_01);
        CustomImageView civ = (CustomImageView)this.findViewById(R.id.civ_02);
        CustomImageView civMusic = (CustomImageView)this.findViewById(R.id.civ_music);
        CustomImageView civVideo = (CustomImageView)this.findViewById(R.id.civ_video);
        CustomImageView civGame = (CustomImageView)this.findViewById(R.id.civ_game);
        CustomImageView civCamera = (CustomImageView)this.findViewById(R.id.civ_camera);
        
        civPic.setOnClickIntent(this);
        civ.setOnClickIntent(this);
        civMusic.setOnClickIntent(this);
        civVideo.setOnClickIntent(this);
        civGame.setOnClickIntent(this);
        civCamera.setOnClickIntent(this);
    }
    
    @Override
    public void onViewClick(CustomImageView view)
    {
        switch (view.getId())
        {
            case R.id.civ_01:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.civ_02:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.civ_music:
                mHandler.removeMessages(SKIP_MUSIC);
                mHandler.sendEmptyMessageDelayed(SKIP_MUSIC, 500);
                break;
            
            case R.id.civ_video:
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.civ_game:
                Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.civ_camera:
                Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    
    private Handler mHandler = new Handler()
    {
        
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case SKIP_MUSIC:
                    startActivity(new Intent(MainActivity.this, MusicPlayer.class));
                    break;
                
                default:
                    break;
            }
        }
        
    };
    
}
