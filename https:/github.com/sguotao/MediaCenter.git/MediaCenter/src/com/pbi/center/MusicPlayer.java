/*
 * File Name:  MusicPlayer.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-3
 * Changed Content:  <Changed Content>
 */
package com.pbi.center;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.pbi.center.adapter.ItemMusicAdapter;
import com.pbi.center.domain.AudioInfo;
import com.pbi.center.global.Config;
import com.pbi.center.service.MusicPlayerService;
import com.pbi.center.utils.LogUtil;
import com.pbi.center.utils.MediaUtil;
import com.pbi.center.view.CircleImageView;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-3]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class MusicPlayer extends Activity implements OnClickListener
{
    private static final String TAG = "MusicPlayer";
    
    private TextView tvMusicTitle;
    
    private TextView tvProgress, tvDuration;
    
    private Button btRepeat, btTime, btPrevious, btPlay, btNext;
    
    private SeekBar sbPlay;
    
    private ListView lvSongs;
    
    private int pos;
    
    private String path;
    
    private CircleImageView civAlbum;
    
    private boolean isPlaying = true;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);
        
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ACTION.ACTION_UPDATE_ALBUM);
        intentFilter.addAction(Config.ACTION.ACTION_UPDATE_DURATION);
        intentFilter.addAction(Config.ACTION.ACTION_UPDATE_PROGRESS);
        registerReceiver(new MusicPlayerReceiver(), intentFilter);
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        tvMusicTitle = (TextView)this.findViewById(R.id.tv_music_title);
        
        btRepeat = (Button)this.findViewById(R.id.bt_repeat_control);
        btTime = (Button)this.findViewById(R.id.bt_time_control);
        btPrevious = (Button)this.findViewById(R.id.bt_previous_music);
        btPlay = (Button)this.findViewById(R.id.bt_play_music);
        btNext = (Button)this.findViewById(R.id.bt_next_music);
        
        btRepeat.setOnClickListener(this);
        btTime.setOnClickListener(this);
        btPrevious.setOnClickListener(this);
        btPlay.setOnClickListener(this);
        btNext.setOnClickListener(this);
        
        tvProgress = (TextView)this.findViewById(R.id.tv_current_progress);
        tvDuration = (TextView)this.findViewById(R.id.tv_final_progress);
        sbPlay = (SeekBar)this.findViewById(R.id.sb_playing_progress);
        sbPlay.setMax(100);
        sbPlay.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }
            
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    Intent intent = new Intent(MusicPlayer.this, MusicPlayerService.class);
                    intent.putExtra("msg", Config.MSG.MSG_MUSIC_SEEK);
                    intent.putExtra("path", path);
                    intent.putExtra("pos", pos);
                    intent.putExtra("progress", progress);
                    startService(intent);
                    
                    LogUtil.show(3, TAG, " seek progress is: " + progress);
                }
            }
        });
        
        civAlbum = (CircleImageView)this.findViewById(R.id.civ_album);
        lvSongs = (ListView)this.findViewById(R.id.lv_song);
        final List<AudioInfo> list = MediaUtil.getAudioInfos(this);
        ItemMusicAdapter musicAdapter = new ItemMusicAdapter(this, list);
        lvSongs.setAdapter(musicAdapter);
        lvSongs.setOnItemClickListener(new OnItemClickListener()
        {
            
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                pos = position;
                path = list.get(position).getPath();
                
                Intent intent = new Intent(MusicPlayer.this, MusicPlayerService.class);
                intent.putExtra("msg", Config.MSG.MSG_MUSIC_PLAY);
                intent.putExtra("path", path);
                intent.putExtra("pos", pos);
                startService(intent);
                
                LogUtil.show(3, TAG, " music title is: " + list.get(position).getTitle());
                tvMusicTitle.setText(list.get(position).getTitle());
                Bitmap albumArt = MediaUtil.getAlbumArt(MusicPlayer.this, list.get(position).getAlbumId());
              //  civAlbum.setImageBitmap(albumArt);
                civAlbum.setImageDrawable(new BitmapDrawable(albumArt));
                civAlbum.startRotate(MusicPlayer.this);
            }
        });
        
    }
    
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(MusicPlayer.this, MusicPlayerService.class);
        switch (v.getId())
        {
            case R.id.bt_repeat_control:
                break;
            
            case R.id.bt_time_control:
                break;
            
            case R.id.bt_previous_music:
                intent.putExtra("msg", Config.MSG.MSG_MUSIC_PREVIOUS);
                startService(intent);
                break;
            
            case R.id.bt_play_music:
                if (isPlaying)
                {
                    intent.putExtra("msg", Config.MSG.MSG_MUSIC_PAUSE);
                    startService(intent);
                    
                    btPlay.setBackgroundResource(R.drawable.pause_music_selector);
                    isPlaying = false;
                    
                    civAlbum.stopRotate();
                }
                else
                {
                    intent.putExtra("msg", Config.MSG.MSG_MUSIC_RESUME);
                    startService(intent);
                    
                    btPlay.setBackgroundResource(R.drawable.play_selector);
                    isPlaying = true;
                    
                    civAlbum.startRotate(MusicPlayer.this);
                }
                break;
            
            case R.id.bt_next_music:
                intent.putExtra("msg", Config.MSG.MSG_MUSIC_NEXT);
                startService(intent);
                break;
        }
    }
    
    class MusicPlayerReceiver extends BroadcastReceiver
    {
        
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            
            /*更新歌曲时长*/
            if(action.equalsIgnoreCase(Config.ACTION.ACTION_UPDATE_DURATION))
            {
                String duration = intent.getStringExtra("duration");
                tvDuration.setText(" / " + duration);
            }
            /*更新播放进度*/
            else if(action.equalsIgnoreCase(Config.ACTION.ACTION_UPDATE_PROGRESS))
            {
                tvProgress.setText(intent.getStringExtra("current"));
                sbPlay.setProgress(intent.getIntExtra("progress", 0));
            }
            /*更新专辑信息*/
            else if (action.equalsIgnoreCase(Config.ACTION.ACTION_UPDATE_ALBUM))
            {
                pos = intent.getIntExtra("pos", 0);
                
//                tvMusicTitle.setText(intent.getStringExtra("name"));
            }
        }
    }
    
}
