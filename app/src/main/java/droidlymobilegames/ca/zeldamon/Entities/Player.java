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
        setupPlayerInfo();
    }

    public void updatePlayer(){
        updatePlayerDirection();//We start by checking what direction the user wants to go then update everything else thereafter
    }

    public void updatePlayerDirection(){
        if (entityRight){
            entityCurrentDirection = "right";
            entityDefaultDirection = "right";
        }else if (entityLeft){
            entityCurrentDirection = "left";
            entityDefaultDirection = "left";
        }else if (entityUp){
            entityCurrentDirection = "up";
            entityDefaultDirection = "up";
        }else if (entityDown){
            entityCurrentDirection = "down";
            entityDefaultDirection = "down";
        }else if (!game.checkbuttonpressed){
            entityCurrentDirection = "buttonreleased";
        }
        updatePlayerAnimations();//Once the user presses the direction they are going we will update their animation accordingly
    }

    public void updatePlayerAnimations(){
        if (game.checkbuttonpressed) {
            entityAnimCounter++;
            if (entityAnimCounter > entityMaxAnimCount) {
                if (entityAnimNum == 1){
                    entityAnimNum = 2;
                }else if (entityAnimNum == 2){
                    entityAnimNum = 3;
                }else if (entityAnimNum == 3){
                    entityAnimNum = 4;
                }else if (entityAnimNum == 4){
                    entityAnimNum = 1;
                }
                entityAnimCounter = 0;
            }
            //Change default sprite based on the direction

        }else {
            if (entityAnimCounter < entityMaxAnimCount +1){
                entityAnimCounter = 0;
                entityAnimNum = 1;
                if (entityAnimNum == 1) {
                    entityAnimNum = 2;
                } else if (entityAnimNum == 2) {
                    entityAnimNum = 3;
                } else if (entityAnimNum == 3) {
                    entityAnimNum = 4;
                }
            }
        }
        //Change this however you'd like but to keep it easy I placed my logic like so
        if (entityCurrentDirection.equals("left")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[0];
            }
            if (entityAnimNum == 2 || entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[1];
            }
        }
        if (entityCurrentDirection.equals("right")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[2];
            }
            if (entityAnimNum == 2 || entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[3];
            }
        }
        if (entityCurrentDirection.equals("up")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[4];
            }
            if (entityAnimNum == 2 || entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[5];
            }
        }
        if (entityCurrentDirection.equals("down")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[6];
            }
            if (entityAnimNum == 2 || entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[7];
            }
        }
        if (entityCurrentDirection.equals("buttonreleased")) {
            if (entityDefaultDirection.equals("right")){
                defaultEntitySprite = entitySprites[2];
            }
            if (entityDefaultDirection.equals("left")){
                defaultEntitySprite = entitySprites[0];
            }
            if (entityDefaultDirection.equals("up")){
                defaultEntitySprite = entitySprites[4];
            }
            if (entityDefaultDirection.equals("down")){
                defaultEntitySprite = entitySprites[6];
            }
        }
        updatePlayerPosXY();//Now we update the player current position in the world
    }

    public void updatePlayerPosXY(){
        switch (entityCurrentDirection){
            case "right": entityWorldX += entitySpeed;break;
            case "left": entityWorldX -= entitySpeed;break;
            case "up": entityWorldY -= entitySpeed;break;
            case "down": entityWorldY += entitySpeed;break;
        }
    }

    public void draw(Canvas canvas){
        if (defaultEntitySprite!=null){
            canvas.drawBitmap(defaultEntitySprite,entityScreenX,
                    entityScreenY,null);
        }

    }

    public void setupPlayerInfo(){//Also like calling initialize Player
        entitySpeed = 10;
        entityScreenX = game.getDisplayWidth(game.getContext())/2 - (game.scaledTileSize/2);
        entityScreenY = game.getDisplayHeight(game.getContext())/2 - (game.scaledTileSize/2);
        entityWorldX = game.scaledTileSize * 3;
        entityWorldY = game.scaledTileSize * 5;
        entityPosX = entityWorldX/game.scaledTileSize;
        entityPosY = entityWorldY/game.scaledTileSize;
        entityMaxAnimCount = 12;
    }

    public void setupSpriteSheet(){//Replace playerwalking_spritesheet with your own spritesheet
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
            //Each sprite tile is given an ID based on the number of images loaded from the spritesheet
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
