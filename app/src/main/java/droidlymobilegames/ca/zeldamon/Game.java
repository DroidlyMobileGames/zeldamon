package droidlymobilegames.ca.zeldamon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Random;

import droidlymobilegames.ca.zeldamon.Entities.EnemyRed;
import droidlymobilegames.ca.zeldamon.Entities.Player;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public SurfaceHolder surfaceHolder;
    public GameLoop gameLoop;
    public boolean showFPS = true;
    public boolean checkbuttonpressed = false;
    public String checkbutton = "";
    public Paint textpaint = new Paint();
    public int defaultTileSize = 0;
    public int scaledTileSize = 0;
    public int maxColumns,maxRows = 0;
    public int gameScreenWidth,gameScreenHeight = 0;
    public Player player;
    public TileManagement tileManagement;
    public CollisionHelper collisionHelper;

    public EnemyRed[] enemyRed;
    public Game(Context context){
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        initializeGame();
        gameLoop = new GameLoop(this,surfaceHolder);
        player = new Player(this);
        tileManagement = new TileManagement(this);
        collisionHelper = new CollisionHelper(this);
        enemyRed = new EnemyRed[50]; //This will allow us to add up to 50 enemies total more can be added
        //addOurFirstEnemy();
    }
    public void initializeGame(){
        textpaint.setColor(Color.WHITE);
        textpaint.setTextSize(50);
        defaultTileSize = 16;//Make sure this matches the dimensions of your tilesheet/spritesheet tiles
        scaledTileSize = 160;//We scale the size for viewing purposes on devices
        maxColumns = 100;//Set to make the worlds X value max to 100 can be increased
        maxRows = 100;//Set to make the worlds Y value max to 100 can be increased
        gameScreenWidth = getDisplayWidth(getContext());//These values is important for multiple uses including placing our player
        gameScreenHeight = getDisplayHeight(getContext());
    }

    public void update(){
        player.updatePlayer();
        for (int red =0; red < enemyRed.length;red++){
            if (enemyRed[red]!=null){
                enemyRed[red].updateEnemy();
            }
        }
    }

    public void addOurFirstEnemy(){
        //enemyRed[0] = new EnemyRed(this);
       // enemyRed[0].entityWorldX = 3 * scaledTileSize;
        //enemyRed[0].entityWorldY = 3 * scaledTileSize;
        for (int i = 0; i < enemyRed.length;i++){
            //Spawn multiple enemies :D
            enemyRed[i] = new EnemyRed(this);
            enemyRed[i].entityWorldX = 3 * scaledTileSize;
            enemyRed[i].entityWorldY = 3 * scaledTileSize;
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        tileManagement.drawTiles(canvas);
        player.draw(canvas);
        for (int red =0; red < enemyRed.length;red++){
            if (enemyRed[red]!=null){
                enemyRed[red].draw(canvas);
            }
        }
        canvas.drawText("FPS ".concat(String.valueOf(gameLoop.getAverageFPS())),50,100,textpaint);
        //canvas.drawText("Enemy Red ID 0 ".concat(String.valueOf(enemyRed[0].entityCurrentDirection).concat(" "
               // .concat(String.valueOf(enemyRed[0].random)))),50,150,textpaint);
        canvas.drawText(String.valueOf(player.entityAttackAnimNum).concat(" "
                .concat(String.valueOf(player.entityAttackAnimCounter))),50,50,textpaint);
    }

    public int getDisplayWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public int getDisplayHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public int setRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
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
