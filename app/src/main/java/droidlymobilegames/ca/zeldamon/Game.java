package droidlymobilegames.ca.zeldamon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Calendar;

import droidlymobilegames.ca.zeldamon.Entities.Player;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public SurfaceHolder surfaceHolder;
    public GameLoop gameLoop;
    public boolean showFPS = true;
    public boolean checkbuttonpressed = false;
    public Paint textpaint = new Paint();
    public int defaultTileSize = 0;
    public int scaledTileSize = 0;
    public int maxColumns,maxRows = 0;
    public int gameScreenWidth,gameScreenHeight = 0;
    public Player player;
    public TileManagement tileManagement;
    public Game(Context context){
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        initializeGame();
        gameLoop = new GameLoop(this,surfaceHolder);
        player = new Player(this);
        tileManagement = new TileManagement(this);
    }
    public void initializeGame(){
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(50);
        defaultTileSize = 16;//Make sure this matches the dimensions of your tilesheet/spritesheet tiles
        scaledTileSize = defaultTileSize * 10;//We scale the size for viewing purposes on devices
        maxColumns = 100;//Set to make the worlds X value max to 100 can be increased
        maxRows = 100;//Set to make the worlds Y value max to 100 can be increased
        gameScreenWidth = getDisplayWidth(getContext());//These values is important for multiple uses including placing our player
        gameScreenHeight = getDisplayHeight(getContext());
    }

    public void update(){
        player.updatePlayer();
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        tileManagement.drawTiles(canvas);
        player.draw(canvas);
        canvas.drawText("FPS ".concat(String.valueOf(gameLoop.getAverageFPS())),50,100,textpaint);
        canvas.drawText(String.valueOf(player.entityPosX)
                .concat(" ".concat(String.valueOf(player.entityPosY))), 50,50,textpaint);
    }

    public int getDisplayWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public int getDisplayHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)){
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
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
