package droidlymobilegames.ca.zeldamon.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import droidlymobilegames.ca.zeldamon.Game;
import droidlymobilegames.ca.zeldamon.R;

public class Player extends EnititesInfo{

    public Game game;

    public Player(Game game){
        this.game = game;
        setupSpriteSheet();
        setupPlayInfo();
    }

    public void updatePlayer(){
        defaultEntitySprite = entitySprites[7];

    }
    public void draw(Canvas canvas){
        if (defaultEntitySprite!=null){
            canvas.drawBitmap(defaultEntitySprite,entityScreenX,
                    entityScreenY,null);
        }

    }
    public void setupPlayInfo(){
        entitySpeed = 10;
        entityScreenX = game.getDisplayWidth(game.getContext())/2 - (game.scaledTileSize/2);
        entityScreenY = game.getDisplayHeight(game.getContext())/2 - (game.scaledTileSize/2);
        entityWorldX = game.scaledTileSize * 3;
        entityWorldY = game.scaledTileSize * 5;
        entityPosX = entityWorldX/game.scaledTileSize;
        entityPosY = entityWorldY/game.scaledTileSize;
    }

    public void setupSpriteSheet(){
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(game.getResources(), R.drawable.playerwalking_spritesheet,bitmapOptions);
        int maxColumns = spritesheet1.getWidth()/game.defaultTileSize;
        int maxRows = spritesheet1.getHeight()/game.defaultTileSize;

        while (currentRow<maxRows){
            entitySprites[numberOftiles] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                    currentColumn * game.defaultTileSize,currentRow * game.defaultTileSize,game.defaultTileSize,game.defaultTileSize),
                    game.scaledTileSize,game.scaledTileSize,false);

            currentColumn ++;
            if (currentColumn == maxColumns){
                currentColumn = 0;
                currentRow ++;
            }
            numberOftiles ++;
            numoftiles.add(numberOftiles);
        }

    }
}
