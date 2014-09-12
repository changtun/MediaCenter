/*
 * File Name:  Config.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-9
 * Changed Content:  <Changed Content>
 */
package com.pbi.center.global;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-9]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class Config
{
    /*
     * message type
     */
    public static class MSG
    {
        public static final int MSG_MUSIC_PLAY = 1;
        
        public static final int MSG_MUSIC_PAUSE = 2;
        
        public static final int MSG_MUSIC_RESUME = 3;
        
        public static final int MSG_MUSIC_PREVIOUS = 4;
        
        public static final int MSG_MUSIC_NEXT = 5;
        
        public static final int MSG_MUSIC_SEEK = 6;
    }
    
    /*
     * log type
     */
    public static class LOG
    {
        public static final int VERBOSE = 1;
        
        public static final int DEBUG = 2;
        
        public static final int INFO = 3;
        
        public static final int WARN = 4;
        
        public static final int ERROR = 5;
    }
    
    /*
     * broadcast action
     */
    public static class ACTION
    {
        public static final String ACTION_UPDATE_PROGRESS = "com.pbi.center.action_update_progress";
        
        public static final String ACTION_UPDATE_DURATION = "com.pbi.center.action_update_duration";
        
        public static final String ACTION_UPDATE_ALBUM = "com.pbi.center.action_update_album";
        
    }
}
