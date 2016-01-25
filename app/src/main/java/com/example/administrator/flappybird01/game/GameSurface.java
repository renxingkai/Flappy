package com.example.administrator.flappybird01.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.administrator.flappybird01.game.layer.Background;
import com.example.administrator.flappybird01.game.layer.Barrier;
import com.example.administrator.flappybird01.game.layer.Player;
import com.example.administrator.flappybird01.game.layer.Score;
import com.example.administrator.flappybird01.game.layer.Start;
import com.example.administrator.flappybird01.utils.Constants;

/**
 * GameSurface
 *
 * @author: Xingkai Ren
 * @time: 2016/1/24  14:49
 */
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public final static String TAG = "GameSurface";

    private int gameState; //游戏当前的状态

    private SurfaceHolder holder;

    private Canvas canvas;
    private Paint paint;

    private Thread thread;
    private boolean flag;

    //游戏图层
    private Background background; //背景
    private Player player; //主角
    private Start start; //开始
    private Barrier barrier;

    //private long start1;
    //private long end;

    private Score score;

    private int scoreMax;

    // protected Camera camera;  //照相机---硬件设备包
    //private boolean ispreview;

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    public GameSurface(Context context) {
        super(context);
        init();
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        holder = getHolder();
        holder.addCallback(this);

        setKeepScreenOn(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);


        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setColor(Color.RED);

        scoreMax = 0;

        //ispreview=false;

    }

    /**
     * 初始化游戏
     */
    private void initGame() {
        gameState = Constants.GAME_START; //设置初始状态为 游戏开始
        background = new Background(this);
        player = new Player(this);
        start = new Start(this);
        barrier = new Barrier(this);
        score = new Score(this);
        score.setScoreMax(scoreMax);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initGame();
        flag = true;
        thread = new Thread(this);
        thread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;


    }

    /**
     * 画图
     *
     * @param canvas
     */
    private void myDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //background.draw(canvas, paint);
        switch (gameState) {
            case Constants.GAME_START:
                player.draw(canvas, paint);
                start.draw(canvas, paint);
                score.draw(canvas, paint);
                break;
            case Constants.GAMING:
                player.draw(canvas, paint);
                barrier.draw(canvas, paint);
                score.draw(canvas, paint);
                break;
            case Constants.GAME_END:
                player.draw(canvas, paint);
                //start.draw(canvas, paint);
                break;
            default:
                break;
        }
    }

    /**
     * 逻辑
     */
    private void logic() {
        switch (gameState) {
            case Constants.GAME_START:
                //start1=System.currentTimeMillis();
                break;
            case Constants.GAMING:
                player.logic();
                barrier.setPlayerradius(player.getRadius());
                barrier.setPlayerx(player.getPlayerx());
                barrier.setPlayery(player.getPlayery());
                barrier.logic();
                score.logic();
                break;
            case Constants.GAME_END:
                //end=System.currentTimeMillis();
                initGame();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameState) {
            case Constants.GAME_START:
                start.onTouchEvent(event);
                break;
            case Constants.GAMING:
                player.onTouchEvent(event);
                break;
            case Constants.GAME_END:

                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();

            canvas = holder.lockCanvas();
            if (null != canvas) {
                myDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
            logic();

            long end = System.currentTimeMillis();

            if (end - start < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }


}
