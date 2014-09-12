/*
 * File Name:  LogUtil.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-9
 * Changed Content:  <Changed Content>
 */
package com.pbi.center.utils;

import com.pbi.center.global.Config;

import android.util.Log;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-9]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class LogUtil
{
    private static boolean isClosed = false;/* 关闭log的打印 */
    
    /**
     * 
     * <Functional overview> <Functional Details>
     * 
     * @param level 1 verbose 2 debug 3 info 4 warn 5 error
     * @param tag
     * @param content
     * @see [Class, Class#Method, Class#Member]
     */
    public static void show(int level, String tag, String content)
    {
        if (isClosed)
        {
            Log.e(tag, "--->> log function closed...");
            return;
        }
        
        switch (level)
        {
            case Config.LOG.VERBOSE:
                Log.v(tag, "--->>> " + content);
                break;
            case Config.LOG.INFO:
                Log.i(tag, "--->>> " + content);
                break;
            case Config.LOG.DEBUG:
                Log.d(tag, "--->>> " + content);
                break;
            case Config.LOG.WARN:
                Log.w(tag, "--->>> " + content);
                break;
            case Config.LOG.ERROR:
                Log.e(tag, "--->>> " + content);
                break;
        }
        
    }
}
