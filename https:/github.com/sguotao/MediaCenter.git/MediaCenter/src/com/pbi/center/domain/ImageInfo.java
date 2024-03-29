/*
 * File Name:  Image.java
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
public class ImageInfo
{
    private int id;
    
    private String title;
    
    private String displayName;
    
    private String mimeType;
    
    private String path;
    
    private long size;
    
    public ImageInfo()
    {
        super();
    }
    
    public ImageInfo(int id, String title, String displayName, String mimeType, String path, long size)
    {
        super();
        this.id = id;
        this.title = title;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
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
    
    public String getDisplayName()
    {
        return displayName;
    }
    
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
    
    public String getMimeType()
    {
        return mimeType;
    }
    
    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
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
