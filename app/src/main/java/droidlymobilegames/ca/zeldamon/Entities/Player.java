package droidlymobilegames.ca.zeldamon.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import droidlymobilegames.ca.zeldamon.Attacks.SwordAttack;
import droidlymobilegames.ca.zeldamon.Game;
import droidlymobilegames.ca.zeldamon.R;

public class Player extends EnititesInfo{

    public Player(Game game){
        this.game = game;
        setupSpriteSheet();
        setupAttackUpDownSpritesheet();
        setupAttackRightLeftSpritesheet();
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
        }else if (entityAttacking){
            entityCurrentDirection = "attackdown";
        }else if (!game.checkbuttonpressed){
            entityCurrentDirection = "buttonreleased";
            entityAttackAnimNum = 1;
            entityAttackAnimCounter = 0;
        }
        if (entityCurrentDirection.equals("attackdown")){
            attackAnimation();
        }else {
            updatePlayerAnimations();
            attackScreenX = entityScreenX;
            attackScreenY = entityScreenY;
        }

        ////Once the user presses the direction they are going we will update their animation accordingly
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
        entityCollision = false;
        game.collisionHelper.checkTileCollision(this,entityCurrentDirection);
        int poop = game.collisionHelper.checkEntityCollision(this,game.enemyLikeLikes,entityCurrentDirection);
        checkEnemy(poop);
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
        }

    }

    public void draw(Canvas canvas){
        if (showHitbox) {
            canvas.drawRect(attackScreenX + hitbox.x,
                    attackScreenY + hitbox.y,
                    attackScreenX + hitbox.x + hitbox.width,
                    attackScreenY + hitbox.y + hitbox.height, hitboxrect);
        }
        if (defaultEntitySprite!=null){
            canvas.drawBitmap(defaultEntitySprite,attackScreenX,
                    attackScreenY,null);
        }
    }

    public void setupPlayerInfo(){//Also like calling initialize Player
        entitySpeed = game.scaledTileSize/16;
        entityScreenX = game.getDisplayWidth(game.getContext())/2 - (game.scaledTileSize/2);
        entityScreenY = game.getDisplayHeight(game.getContext())/2 - (game.scaledTileSize/2);
        entityWorldX = game.scaledTileSize * 3;
        entityWorldY = game.scaledTileSize * 3;
        entityPosX = entityWorldX/game.scaledTileSize;
        entityPosY = entityWorldY/game.scaledTileSize;
        entityMaxAnimCount = 8;
        entityDefaultDirection = "right";
        //16 is just a random number I chose based on my players sprite to make collision look normal
        hitbox.x = 4;
        hitbox.width = game.scaledTileSize-8;
        hitbox.y = 32;
        hitbox.height = game.scaledTileSize-16;
        hitboxrect.setColor(Color.BLUE);
        hitboxrect.setStyle(Paint.Style.FILL);
        entityAttackMaxAnim = 4;
        attackScreenX = entityScreenX;
        attackScreenY = entityScreenY;

        entityDefaultHitboxLeft = (int) hitbox.x;
        entityDefaultHitboxTop = (int) hitbox.y;
        entityDefaultHitboxRight = (int) hitbox.width;
        entityDefaultHitboxBottom = (int) hitbox.height;
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

    public void setupAttackUpDownSpritesheet(){//Replace playerwalking_spritesheet with your own spritesheet
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(game.getResources(), R.drawable.attack_down_up_spritesheet,bitmapOptions);
        int maxColumns = spritesheet1.getWidth()/game.defaultTileSize;
        int maxRows = spritesheet1.getHeight()/game.defaultTileSize;

        //Attack down sprite
        entityAttackSprites[0] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,0 * 16,32,16),
                game.scaledTileSize * 2,game.scaledTileSize,false);
        entityAttackSprites[1] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,1 * 16,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);
        entityAttackSprites[2] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,48,32,32),
                game.scaledTileSize * 2,game.scaledTileSize *2 ,false);
        //Attack up sprite
        entityAttackSprites[3] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,3 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);
        entityAttackSprites[4] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,4 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);
        entityAttackSprites[5] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,5 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize *2 ,false);

    }

    public void setupAttackRightLeftSpritesheet(){//Replace playerwalking_spritesheet with your own spritesheet
        Bitmap spritesheet1;
        int currentColumn = 0;
        int currentRow = 0;
        int numberOftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        spritesheet1 = BitmapFactory.decodeResource(game.getResources(), R.drawable.attack_right_left_spritesheet,bitmapOptions);
        int maxColumns = spritesheet1.getWidth()/game.defaultTileSize;
        int maxRows = spritesheet1.getHeight()/game.defaultTileSize;

        //Attack right sprite 32 is the pixel width of the image being cropped out
        entityAttackSprites[6] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,0 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);
        entityAttackSprites[7] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,1 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);

        entityAttackSprites[8] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,2 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize *2 ,false);

        //Attack left sprite
        entityAttackSprites[9] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,3 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);
        entityAttackSprites[10] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,4 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize * 2,false);
        entityAttackSprites[11] = Bitmap.createScaledBitmap(Bitmap.createBitmap(spritesheet1,
                        0 * 32,5 * 32,32,32),
                game.scaledTileSize * 2,game.scaledTileSize *2 ,false);


        //When working with bigger sprites start at 0 y and 0 x depending on the size of the sprite
        //you will calculate the distance at which you want to crop the image from the spritesheet
        //example if you sprite is 32x32 pixels and the width of the spritesheet is 32 pixels you
        //will get 0 at the x value and 0 at the y value

    }

    public void attackAnimation(){

        if (entityDefaultDirection.equals("down")) {
            attackScreenX = entityScreenX - game.scaledTileSize;
            game.swordAttack = new SwordAttack(game);
            game.swordAttack.entityWorldX = entityWorldX + hitbox.x;
            game.swordAttack.entityWorldY = entityWorldY + hitbox.y + hitbox.height;
            game.swordAttack.entityCurrentDirection = "down";
        }
        if (entityDefaultDirection.equals("up")){
            attackScreenY = entityScreenY - game.scaledTileSize;
            game.swordAttack = new SwordAttack(game);
            game.swordAttack.entityWorldX = entityWorldX + hitbox.x;
            game.swordAttack.entityWorldY = entityWorldY - (hitbox.height-hitbox.y);
            game.swordAttack.entityCurrentDirection = "up";
        }
        if (entityDefaultDirection.equals("right")){
            attackScreenX = entityScreenX;
            attackScreenY = entityScreenY - game.scaledTileSize;
            game.swordAttack = new SwordAttack(game);
            game.swordAttack.entityWorldX = entityWorldX + hitbox.width;
            game.swordAttack.entityWorldY = entityWorldY + hitbox.y;
        }
        if (entityDefaultDirection.equals("left")){
            attackScreenX = entityScreenX - game.scaledTileSize;
            attackScreenY = entityScreenY - game.scaledTileSize;
            game.swordAttack = new SwordAttack(game);
            game.swordAttack.entityWorldX = entityWorldX - hitbox.width + hitbox.x;
            game.swordAttack.entityWorldY = entityWorldY + hitbox.y;
        }

        if (game.checkbuttonpressed){
            entityAttackAnimCounter ++;
            if (entityAttackAnimCounter>entityAttackMaxAnim){

                if (entityAttackAnimNum == 1){
                    entityAttackAnimNum = 2;
                }else if (entityAttackAnimNum == 2){
                    entityAttackAnimNum = 3;
                }else if (entityAttackAnimNum == 3){
                    entityAttackAnimNum = 4;
                }
                entityAttackAnimCounter = 0;
            }
        }else if (entityAttackAnimCounter < entityAttackMaxAnim + 1){
            entityAttackAnimNum = 1;
            //entityAttackAnimCounter = 0;
            if (entityAttackAnimNum == 1){
                entityAttackAnimNum = 2;
            }else if (entityAttackAnimNum == 2){
                entityAttackAnimNum = 3;
            }else if (entityAttackAnimNum == 3){
                entityAttackAnimNum = 4;
            }
        }

        if (entityAttackAnimNum == 1){
            if (entityDefaultDirection.equals("down")){
                defaultEntitySprite = entityAttackSprites[0];
            }
            if (entityDefaultDirection.equals("up")){
                defaultEntitySprite = entityAttackSprites[3];
            }
            if (entityDefaultDirection.equals("right")){
                defaultEntitySprite = entityAttackSprites[6];
            }
            if (entityDefaultDirection.equals("left")){
                defaultEntitySprite = entityAttackSprites[9];
            }

        }
        if (entityAttackAnimNum == 2){
            if (entityDefaultDirection.equals("down")){
                defaultEntitySprite = entityAttackSprites[1];
            }
            if (entityDefaultDirection.equals("up")){
                defaultEntitySprite = entityAttackSprites[4];
            }
            if (entityDefaultDirection.equals("right")){
                defaultEntitySprite = entityAttackSprites[7];
            }
            if (entityDefaultDirection.equals("left")){
                defaultEntitySprite = entityAttackSprites[10];
            }

        }
        if (entityAttackAnimNum == 3){
            if (entityDefaultDirection.equals("down")){
                defaultEntitySprite = entityAttackSprites[2];
            }
            if (entityDefaultDirection.equals("up")){
                defaultEntitySprite = entityAttackSprites[5];
            }
            if (entityDefaultDirection.equals("right")){
                defaultEntitySprite = entityAttackSprites[8];
            }
            if (entityDefaultDirection.equals("left")){
                defaultEntitySprite = entityAttackSprites[11];
            }


        }
        if (entityAttackAnimNum == 4){
            game.checkbuttonpressed = false;
            entityAttacking = false;
            if (game.checkbutton.equals("down")){
                entityDown = true;
                game.checkbuttonpressed = true;
            }
            if (game.checkbutton.equals("up")){
                entityUp = true;
                game.checkbuttonpressed = true;
            }
            if (game.checkbutton.equals("right")){
                entityRight = true;
                game.checkbuttonpressed = true;
            }
            if (game.checkbutton.equals("left")){
                entityLeft = true;
                game.checkbuttonpressed = true;
            }
        }
    }

    public void checkEnemy(final int i){
        if (i != 999){

        }
    }
}
