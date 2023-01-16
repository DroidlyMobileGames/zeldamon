package droidlymobilegames.ca.zeldamon.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import droidlymobilegames.ca.zeldamon.Game;
import droidlymobilegames.ca.zeldamon.R;

public class EnemyRed extends EnititesInfo{

    public EnemyRed(Game game){
        this.game = game;
        setupSpriteSheet();
        setupPlayerInfo();
    }

    public void updateEnemy(){
        updatePlayerDirection();//We start by checking what direction the user wants to go then update everything else thereafter
    }

    public void setRandomDirections(){
        setNextDirection ++;

        if (setNextDirection > nextDirectionSpeed){
            setNextDirection = 0;
            random = game.setRandom(0,200);
            if (random > 0 && random < 26 || random > 99 && random < 126 || random > 149 && random < 201){
                entityIdle = true;
            }else

            if (random > 25 && random < 51){
                entityRight = true;
            }else
            if (random > 50 && random < 76){
                entityLeft = true;
            }else
            if (random > 75 && random < 100){
               entityUp = true;
            }else
            if (random > 125 && random < 150){
                entityDown = true;
            }
        }




    }

    public void updatePlayerDirection(){
        setRandomDirections();//Video 11 adding enemy 1
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
        }else if (entityIdle){
            entityCurrentDirection = "idle";
        }
        updatePlayerAnimations();//Once the user presses the direction they are going we will update their animation accordingly
    }

    public void updatePlayerAnimations(){
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
        //Change this however you'd like but to keep it easy I placed my logic like so
        if (entityCurrentDirection.equals("left")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[6];
            }
            if (entityAnimNum == 2) {
                defaultEntitySprite = entitySprites[7];
            }
            if (entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[8];
            }
        }
        if (entityCurrentDirection.equals("right")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[9];
            }
            if (entityAnimNum == 2) {
                defaultEntitySprite = entitySprites[10];
            }
            if (entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[11];
            }
        }
        if (entityCurrentDirection.equals("up")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[3];
            }
            if (entityAnimNum == 2) {
                defaultEntitySprite = entitySprites[4];
            }
            if (entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[5];
            }
        }
        if (entityCurrentDirection.equals("down")) {
            if (entityAnimNum == 1 || entityAnimNum == 3) {
                defaultEntitySprite = entitySprites[0];
            }
            if (entityAnimNum == 2) {
                defaultEntitySprite = entitySprites[1];
            }
            if (entityAnimNum == 4) {
                defaultEntitySprite = entitySprites[2];
            }
        }
        if (entityCurrentDirection.equals("idle")) {
            if (entityDefaultDirection.equals("right")){
                defaultEntitySprite = entitySprites[9];
            }
            if (entityDefaultDirection.equals("left")){
                defaultEntitySprite = entitySprites[6];
            }
            if (entityDefaultDirection.equals("up")){
                defaultEntitySprite = entitySprites[3];
            }
            if (entityDefaultDirection.equals("down")){
                defaultEntitySprite = entitySprites[0];
            }
        }
        updatePlayerPosXY();//Now we update the player current position in the world
    }

    public void updatePlayerPosXY(){
        entityCollision = false;
        game.collisionHelper.checkTileCollision(this,entityCurrentDirection);
        if (!entityCollision) {
            switch (entityCurrentDirection) {
                case "right":
                    entityWorldX += entitySpeed;
                    break;
                case "left":
                    entityWorldX -= entitySpeed;
                    break;
                case "up":
                    entityWorldY -= entitySpeed;
                    break;
                case "down":
                    entityWorldY += entitySpeed;
                    break;
            }
        }else {
            entityIdle = true;
            entityDown = false;
            entityUp = false;
            entityRight = false;
            entityLeft = false;
        }

    }

    public void draw(Canvas canvas){

        entityScreenX = entityWorldX - game.player.entityWorldX + game.player.entityScreenX;
        entityScreenY = entityWorldY - game.player.entityWorldY + game.player.entityScreenY;

        //Check if play is withing camera bounds otherwise don't render enemy
        if (entityWorldX + game.gameScreenWidth > game.player.entityWorldX - game.player.entityScreenX &&
                entityWorldX - game.gameScreenWidth < game.player.entityWorldX + game.player.entityScreenX &&
                entityWorldY + game.gameScreenHeight > game.player.entityWorldY - game.player.entityScreenY &&
                entityWorldY - game.gameScreenHeight < game.player.entityWorldY + game.player.entityScreenY) {
            if (showHitbox) {
                canvas.drawRect(entityScreenX + hitbox.left,
                        entityScreenY + hitbox.top,
                        entityScreenX + hitbox.left + hitbox.right,
                        entityScreenY + hitbox.top + hitbox.bottom, hitboxrect);
            }
            if (defaultEntitySprite != null) {
                canvas.drawBitmap(defaultEntitySprite, entityScreenX,
                        entityScreenY, null);
            }
        }


    }

    public void setupPlayerInfo(){//Also like calling initialize Player
        entitySpeed = 6;
        entityScreenX = game.getDisplayWidth(game.getContext())/2 - (game.scaledTileSize/2);
        entityScreenY = game.getDisplayHeight(game.getContext())/2 - (game.scaledTileSize/2);
        entityWorldX = game.scaledTileSize * 4;
        entityWorldY = game.scaledTileSize * 3;
        entityPosX = entityWorldX/game.scaledTileSize;
        entityPosY = entityWorldY/game.scaledTileSize;
        entityMaxAnimCount = 12;
        entityDefaultDirection = "right";
        //16 is just a random number I chose based on my players sprite to make collision look normal
        hitbox.left = 16;
        hitbox.right = game.scaledTileSize-16;
        hitbox.top = 32;
        hitbox.bottom = game.scaledTileSize-32;
        hitboxrect.setColor(Color.BLUE);
        hitboxrect.setStyle(Paint.Style.FILL);
        setNextDirection = 249;
        nextDirectionSpeed = 250;
    }

    public void setupSpriteSheet(){//Replace playerwalking_spritesheet with your own spritesheet
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(game.getResources(), R.drawable.spritesheet_red,bitmapOptions);
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
