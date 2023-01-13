package droidlymobilegames.ca.zeldamon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public SurfaceHolder surfaceHolder;
    public GameLoop gameLoop;
    public boolean showFPS = false;
    public Paint textpaint = new Paint();
    public int funint = 0;

    public Game(Context context){
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this,surfaceHolder);
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(25);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        funint += 10;
        canvas.drawText("DROIDLY MOBILE IS AWESOME", funint,50,textpaint);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)){
            gameLoop = new GameLoop(this,surfaceHolder);
        }
        gameLoop.startLoop();
    }

    public int getDisplayWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public int getDisplayHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
