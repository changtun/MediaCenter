/*
 * File Name:  Camera.java
 * Copyright:  Beijing Jaeger Communication Electronic Technology Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * Descriptions:  <Descriptions>
 * Changed By:  
 * Changed Time:  2014-9-3
 * Changed Content:  <Changed Content>
 */
package com.pbi.center;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * <Functional overview> <Functional Details>
 * 
 * @author
 * @version [Version Number, 2014-9-3]
 * @see [Relevant Class/Method]
 * @since [Product/Module Version]
 */
public class CameraActivity extends Activity
{
    SurfaceView sv;
    
    Camera camera;
    
    Button bt_takepic, bt_autofoucus;
    
    SurfaceHolder holder;
    
    boolean isvisiable = false;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 指定 界面没有title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.camera);
        sv = (SurfaceView)this.findViewById(R.id.sv);
        holder = sv.getHolder();
        holder.setFormat(PixelFormat.TRANSPARENT);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new MySurfaceCallback());
        bt_takepic = (Button)this.findViewById(R.id.takepic);
        bt_autofoucus = (Button)this.findViewById(R.id.autofocus);
        sv.setOnClickListener(new OnClickListener()
        {
            
            public void onClick(View v)
            {
                System.out.println("click");
                if (isvisiable)
                {
                    bt_autofoucus.setVisibility(View.INVISIBLE);
                    bt_takepic.setVisibility(View.INVISIBLE);
                    isvisiable = false;
                }
                else
                {
                    bt_autofoucus.setVisibility(View.VISIBLE);
                    bt_takepic.setVisibility(View.VISIBLE);
                    isvisiable = true;
                }
            }
        });
    }
    
    private class MySurfaceCallback implements Callback
    {
        
        public void surfaceCreated(SurfaceHolder holder)
        {
            try
            {
                camera = Camera.open();
                Camera.Parameters params = camera.getParameters();
                // System.out.println(params.flatten());
                params.setPictureSize(1024, 768);
                params.setPreviewFrameRate(5);
                params.setPreviewSize(640, 480);
                params.setJpegQuality(80);
                camera.setParameters(params);
                // 照相机的预览界面显示到哪里
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
            
        }
        
        public void surfaceDestroyed(SurfaceHolder holder)
        {
            camera.release();
            camera = null;
        }
        
    }
    
    public void click(View view)
    {
        switch (view.getId())
        {
            case R.id.autofocus:
                camera.autoFocus(null);
                break;
            case R.id.takepic:
                camera.takePicture(null, null, new PictureCallback()
                {
                    
                    public void onPictureTaken(byte[] data, Camera camera)
                    {
                        try
                        {
                            File file =
                                new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(data);
                            fos.close();
                            camera.startPreview();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}
