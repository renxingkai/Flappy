package com.example.administrator.flappybird01.game.layer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.flappybird01.R;
import com.example.administrator.flappybird01.game.GameSurface;
import com.example.administrator.flappybird01.utils.Assist;
import com.example.administrator.flappybird01.utils.Constants;

import java.util.Random;

/**
 * Barrier
 *
 * @author: Xingkai Ren
 * @time: 2016/1/24  14:56
 */
public class Barrier extends BaseLayer {

    private float spaceH;//障碍的间隔
    private float distance;//障碍水平间隔
    /**
     * 障碍的坐标+宽，高
     */
    private float Barrier1x;

    private float Barrier2x, Barrier2y;

    private float BarrierW;
    private float Barrier1H;
    private float BarrierY;

    private float Barrier2H, Barrier2W;

    private float speed;//障碍移动速度

    private float playerx;//主角的X
    private float playery;//主角Y
    private float playerradius;//主角的半径

    public Barrier(GameSurface surface) {
        super(surface);

        speed = 15;
        spaceH = screenH / 4;
        BarrierW = 130;
        distance = screenW / 2 - BarrierW/2;//两个障碍的距离
        BarrierY = 0;

        Barrier1x = screenW + 200;
        Barrier1H = getBarrierH();

        Barrier2x = Barrier1x + distance + BarrierW;//第二个障碍的坐标
        Barrier2y = 0;
        Barrier2H = getBarrierH();

    }

    @Override
    public void draw(Canvas canvas, Paint paint) {

        paint.setColor(Assist.getColor(res, R.color.SpringGreen));
        canvas.drawRect(Barrier1x, BarrierY, Barrier1x + BarrierW, BarrierY + Barrier1H, paint);//第一个上
        canvas.drawRect(Barrier1x, Barrier1H + spaceH, Barrier1x + BarrierW, screenH, paint);//第一个下

        canvas.drawRect(Barrier2x, BarrierY, Barrier2x + BarrierW, BarrierY + Barrier2H, paint);//第二个上
        canvas.drawRect(Barrier2x, Barrier2H + spaceH, Barrier2x + BarrierW, screenH, paint);//第二个下


    }

    @Override
    public void logic() {

        Barrier1x -= speed;
        Barrier2x -= speed;
        //Barrier2x = Barrier1x + distance + BarrierW;//第二个障碍的坐标
        /**
         * 碰撞检测，检测上下边界
         */
        if (Barrier1x + BarrierW < 0) {
            Barrier1x = screenW;
            Barrier1H = getBarrierH();
        }
        if (Barrier2x + BarrierW < 0) {
            Barrier2x = screenW;
            Barrier2H = getBarrierH();
        }
        //判断主角与障碍的碰撞情况
        boolean isColl=circleAndRect(playerx,playery,playerradius,Barrier1x,BarrierY,BarrierW,Barrier1H);
        boolean isColl2=circleAndRect(playerx,playery,playerradius,Barrier1x,Barrier1H+spaceH,BarrierW,screenH-Barrier1H-spaceH);
        boolean isColl3=circleAndRect(playerx,playery,playerradius,Barrier2x,BarrierY,BarrierW,Barrier2H);
        boolean isColl4=circleAndRect(playerx,playery,playerradius,Barrier2x,Barrier2H+spaceH,BarrierW,screenH-Barrier2H-spaceH);
        if(isColl||isColl2||isColl3||isColl4){
            surface.setGameState(Constants.GAME_END);
        }
    }

    /**
     * 圆与矩形间的碰撞检测
     *
     * @param circleX
     * @param circleY
     * @param circleR
     * @param rectX
     * @param rectY
     * @param rectW
     * @param rectH
     * @return
     */
    private boolean circleAndRect(float circleX, float circleY, float circleR, float rectX, float
            rectY, float rectW, float rectH) {
        if (circleX + circleR < rectX) {
            return false;
        } else if (circleX - circleR > rectX + rectW) {
            return false;
        } else if (circleY + circleR < rectY) {
            return false;
        } else if (circleY - circleR > rectY + rectH) {
            return false;
        } else if (Math.pow(rectX - circleX, 2) + Math.pow(rectY - circleY, 2) > circleR *
                circleR && circleX < rectX && circleY < rectX) {
            return false;
        } else if (Math.pow(rectX + rectW - circleX, 2) + Math.pow(rectY - circleY, 2) > circleR *
                circleR && circleX > rectX + rectW && circleY < rectY) {
            return false;
        } else if (Math.pow(rectX - circleX, 2) + Math.pow(rectY + rectH - circleY, 2) > circleR *
                circleR && circleX < rectX && circleY > rectY + rectH) {
            return false;
        } else if (Math.pow(rectX + rectW - circleX, 2) + Math.pow(rectY + rectH - circleY, 2) >
                circleR * circleR && circleX > rectX + rectW && circleY > rectY + rectH) {
            return false;
        }

        return true;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {




    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {

    }

    private float getBarrierH(){
       return new Random().nextInt((int) (screenH - spaceH-200));
    }

    public float getPlayerx() {
        return playerx;
    }

    public void setPlayerx(float playerx) {
        this.playerx = playerx;
    }

    public float getPlayery() {
        return playery;
    }

    public void setPlayery(float playery) {
        this.playery = playery;
    }

    public float getPlayerradius() {
        return playerradius;
    }

    public void setPlayerradius(float playerradius) {
        this.playerradius = playerradius;
    }
}
