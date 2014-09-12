/*
 * File Name:  AudioInfo.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-8-30
 * Changed Content:  <Changed Content>
 */
package com.pbi.center.domain;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-8-30]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class AudioInfo
{
    private long id;/* 歌曲id */
    
    private String title;/* 歌曲名称 */
    
    private long albumId;/* 专辑id */
    
    private String album;/* 歌曲专辑名称 */
    
    private String artist;/* 歌手名称 */
    
    private String path;/* 歌曲路径 */
    
    private String displayName;/* 显示名称 */
    
    private long duration;/* 歌曲播放时长 */
    
    private long size;/* 歌曲文件大小 */
    
    public AudioInfo()
    {
        super();
    }
    
    public AudioInfo(long id, String title, long albumId, String album, String artist, String path, String displayName,
        long duration, long size)
    {
        super();
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.album = album;
        this.artist = artist;
        this.path = path;
        this.displayName = displayName;
        this.duration = duration;
        this.size = size;
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getAlbum()
    {
        return album;
    }
    
    public void setAlbum(String album)
    {
        this.album = album;
    }
    
    public String getArtist()
    {
        return artist;
    }
    
    public void setArtist(String artist)
    {
        this.artist = artist;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getDisplayName()
    {
        return displayName;
    }
    
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
    
    public long getAlbumId()
    {
        return albumId;
    }
    
    public void setAlbumId(long albumId)
    {
        this.albumId = albumId;
    }
    
    public long getDuration()
    {
        return duration;
    }
    
    public void setDuration(long duration)
    {
        this.duration = duration;
    }
    
    public long getSize()
    {
        return size;
    }
    
    public void setSize(long size)
    {
        this.size = size;
    }
    
}
