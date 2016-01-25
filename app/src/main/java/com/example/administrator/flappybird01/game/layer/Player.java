package com.example.administrator.flappybird01.game.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.flappybird01.R;
import com.example.administrator.flappybird01.game.GameSurface;
import com.example.administrator.flappybird01.utils.Assist;
import com.example.administrator.flappybird01.utils.Constants;

/**
 * Player
 *
 * @author: Xingkai Ren
 * @time: 2016/1/24  14:57
 */
public class Player extends BaseLayer {

    private float playerx, playery;
    private float radius;

    private float speed;
    private float acc;

    private final float  distance=10;


    public Player(GameSurface surface) {
        super(surface);
        playerx = screenW / 3;
        playery = screenH / 2;

        radius = 30;
        speed = 20;
        acc = 5;
    }

    public float getPlayerx() {
        return playerx;
    }

    public float getPlayery() {
        return playery;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {

        paint.setColor(Assist.getColor(res, R.color.Black));

        //canvas.drawCircle(playerx, playery, radius, paint);

        switch (surface.getGameState()) {
            case Constants.GAME_START:
                canvas.drawCircle(screenW / 3, screenH / 2, radius, paint);
                break;
            case Constants.GAMING:
                canvas.drawCircle(playerx, playery, radius, paint);

                break;
            case Constants.GAME_END:

                canvas.drawCircle(screenW / 3, screenH / 2, radius, paint);
                break;
            default:
                break;
        }

    }

    @Override
    public void logic() {
        playery += speed;
        speed += acc;
        //上边界碰撞检测
        if (playery - radius < 0 || playery + radius > screenH) {
            surface.setGameState(Constants.GAME_END);
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        //playery-=distance;
        speed=-40;

    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }
}
