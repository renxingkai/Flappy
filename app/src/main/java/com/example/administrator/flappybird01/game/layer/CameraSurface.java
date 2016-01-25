package com.example.administrator.flappybird01.game.layer;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * CameraSurface
 *
 * @author:  Xingkai Ren
 * @time:2016/1/23 9:27
 */
public class CameraSurface extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TAG = "CameraSurface...";

    private SurfaceHolder holder;

    protected Camera camera;  //照相机---硬件设备包

    private boolean ispreview;



    /**
     * 初始化
     */
    public void init() {

        holder = getHolder();
        holder.addCallback(this);//添加监听器到this

        ispreview=false;

    }

    public CameraSurface(Context context) {             //MainActivity中setContentView中调用
        super(context);
        init();
    }


    public CameraSurface(Context context, AttributeSet attrs) {     //xml中布局调用
        super(context, attrs);
        init();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        camera = Camera.open();//若不传递参数默认后置摄像头

        if (null != camera) {
            try {
                //camera.setDisplayOrientation(90);
                camera.setPreviewDisplay(holder);//设置预览

            } catch (IOException e) {
                e.printStackTrace();
                camera.release();
                camera=null;
            }
            Camera.Parameters parameters=camera.getParameters();
            parameters.setPreviewSize(getWidth(),getHeight());//设置预览尺寸
            camera.setParameters(parameters);//传递预览尺寸参数


            camera.startPreview();//开始预览
            ispreview=true;//开始预览变量赋为真
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != camera) {           //相机开启了
            if (ispreview) {            //并且判断是处在预览状态下
                camera.stopPreview();//停止照相机
            }
            camera.release();//释放照相机
            camera = null;
        }
    }
}
