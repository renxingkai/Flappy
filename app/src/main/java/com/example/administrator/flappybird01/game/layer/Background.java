package com.example.administrator.flappybird01.game.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.flappybird01.R;
import com.example.administrator.flappybird01.game.GameSurface;
import com.example.administrator.flappybird01.utils.Assist;

/**
 * Background
 *
 * @author: Xingkai Ren
 * @time: 2016/1/24  14:55
 */
public class Background extends BaseLayer{



    public Background(GameSurface surface) {
        super(surface);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Assist.getColor(res, R.color.Cyan));
        // 画纯色背景
        canvas.drawRect(0, 0, screenW, screenH, paint);
//        paint.setTextSize(50);
//        paint.setColor(Color.RED);
//        canvas.drawText("0", 100, 100, paint);
    }

    @Override
    public void logic() {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }
}
