/*
 * File Name:  MediaUtil.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-9
 * Changed Content:  <Changed Content>
 */
package com.pbi.center.utils;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.pbi.center.R;
import com.pbi.center.domain.AudioInfo;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-9]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class MediaUtil
{
    private static final String TAG = "MediaUtil";

    public static List<AudioInfo> getAudioInfos(Context context)
    {
        Cursor cursor =
            context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        
        List<AudioInfo> mp3Infos = new ArrayList<AudioInfo>();
        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToNext();
            AudioInfo mp3Info = new AudioInfo();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            long albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic != 0)
            {
                mp3Info.setId(id);
                mp3Info.setTitle(title);
                mp3Info.setArtist(artist);
                mp3Info.setAlbum(album);
                mp3Info.setDisplayName(displayName);
                mp3Info.setAlbumId(albumId);
                mp3Info.setDuration(duration);
                mp3Info.setSize(size);
                mp3Info.setPath(url);
                mp3Infos.add(mp3Info);
            }
        }
        return mp3Infos;
    }
    
    private static String formatStr(int length, int number)
    {
        String format = "%0" + length + "d";
        return String.format(format, number);
    }
    
    public static String formatTime(int time)
    {
        int min = time / (1000 * 60);
        int sec = time % (1000 * 60);
        String str = formatStr(5, sec);
        return formatStr(2, min) + ":" + str.trim().substring(0, 2);
    }
    
    public static Bitmap getAlbumArt(Context context, long albumId)
    {
        Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(albumArtUri, albumId);
        
        FileDescriptor fd = null;
        Bitmap bm = null;
        ParcelFileDescriptor pfd;
        try
        {
            pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd != null)
            {
                fd = pfd.getFileDescriptor();
            }
            
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.outHeight = 300;
            options.outWidth = 300;
            options.inJustDecodeBounds = false;
            
            // 根据options参数，减少所需要的内存
            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
        }
        catch (FileNotFoundException e)
        {
            LogUtil.show(5, TAG, " album art not found, return default...");
        }
        
        //返回默认图片
        if(null == bm)
        {
            bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        }
        return bm;
    }
}
