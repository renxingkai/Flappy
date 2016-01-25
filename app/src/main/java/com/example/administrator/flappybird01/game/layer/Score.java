package com.example.administrator.flappybird01.game.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.flappybird01.game.GameSurface;
import com.example.administrator.flappybird01.utils.Constants;

/**
 * Score
 *
 * @author: Xingkai Ren
 * @time: 2016/1/24  14:57
 */
public class Score extends BaseLayer {

    private float scorex, scorey;
    private int score;

    private long start;
    private long end;

    private boolean isStart;

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    private int scoreMax;

    public Score(GameSurface surface) {
        super(surface);
        score = 0;
        scorex = screenW / 2;
        scorey = screenH / 3;
        isStart = true;
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {

        paint.setTextSize(60);
        paint.setColor(Color.RED);
        switch (surface.getGameState()){
            case Constants.GAME_START:
                canvas.drawText(scoreMax+"s", scorex, scorey,paint);
                break;
            case Constants.GAMING:
                canvas.drawText(scoreMax+"s", scorex, scorey,paint);
                break;
            case Constants.GAME_END:
                break;
            default:break;
        }


    }

    @Override
    public void logic() {
        if (isStart ) {
            start = System.currentTimeMillis();
            isStart = false;
        }
        end=System.currentTimeMillis();
        score= (int) ((end-start)/1000);
        //保存最高分
        if(score>scoreMax){
            surface.setScoreMax(score);
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }
}
