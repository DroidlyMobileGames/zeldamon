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
    public int funintX,funintY = 0;

    public Game(Context context){
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this,surfaceHolder);
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(50);
        funintY = 50;
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        funintX += 10;
        if (funintX > 1080){
            funintY +=50;
            funintX = 0;
        }
        canvas.drawText("DROIDLY MOBILE IS AWESOME", funintX,funintY,textpaint);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)){
            gameLoop = new GameLoop(this,surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
