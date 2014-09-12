/*
 * File Name:  MainItemAdapter.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-8-29
 * Changed Content:  <Changed Content>
 */
package com.pbi.center.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pbi.center.R;
import com.pbi.center.domain.AudioInfo;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-8-29]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class ItemMusicAdapter extends BaseAdapter
{
    private List<AudioInfo> list;
    
    private LayoutInflater inflater;
    
    public ItemMusicAdapter(Context context, List<AudioInfo> list)
    {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    
    /**
     * Override Method
     * 
     * @return
     */
    @Override
    public int getCount()
    {
        return list.size();
    }
    
    /**
     * Override Method
     * 
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    
    /**
     * Override Method
     * 
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    /**
     * Override Method
     * 
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(R.layout.item_music, null);
        TextView tvSongName = (TextView)view.findViewById(R.id.tv_audio_name);
        TextView tvArtistName = (TextView)view.findViewById(R.id.tv_audio_artist);
        if (null != list)
        {
            tvSongName.setText(list.get(position).getDisplayName());
            tvArtistName.setText(list.get(position).getArtist());
        }
        return view;
    }
    
}
