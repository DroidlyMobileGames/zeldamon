package droidlymobilegames.ca.zeldamon;

import droidlymobilegames.ca.zeldamon.Entities.EnititesInfo;

public class CollisionHelper extends EnititesInfo {
    public int checkPlayerLeftSide = 0;
    public int checkPlayerRightSide = 0;
    public int setPlayerLeftX;
    public int setPlayerRightX;
    public int checkPlayerTopSide;
    public int checkPlayerBottomSide;
    public int setPlayerTopY;
    public int setPlayerBottomY;
    public CollisionHelper(Game game){
        this.game = game;
    }

    public void checkTileCollision(EnititesInfo entity,final String entityDirection){
        //Check Right & Left
        checkPlayerLeftSide = (int) (entity.entityWorldX + entity.hitbox.x);
        checkPlayerRightSide = (int) (entity.entityWorldX + entity.hitbox.x + entity.hitbox.width);
        setPlayerLeftX = checkPlayerLeftSide/game.scaledTileSize;
        setPlayerRightX = checkPlayerRightSide/game.scaledTileSize;
        //Check Up & Down
        checkPlayerTopSide = (int) (entity.entityWorldY + entity.hitbox.y);
        checkPlayerBottomSide = (int) (entity.entityWorldY + entity.hitbox.y + entity.hitbox.height);
        setPlayerTopY = checkPlayerTopSide/game.scaledTileSize;
        setPlayerBottomY = checkPlayerBottomSide/game.scaledTileSize;//To round the current Y value we can -1 so that when the player is walking left or right they don't collide
        //When we go left or right we want to check the top tile and bottom tile position so that the player cannot walk through the tile halfway

        switch (entityDirection){
            case "left":
                setPlayerLeftX = (checkPlayerLeftSide - entity.entitySpeed)/game.scaledTileSize;
                setPlayerTopY = (checkPlayerTopSide+entity.entitySpeed)/game.scaledTileSize;
                setPlayerBottomY = (checkPlayerBottomSide-entity.entitySpeed)/game.scaledTileSize;
                checkTile1 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerTopY];
                checkTile2 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerBottomY];
                if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                    entity.entityCollision = true;
                }
                break;
            case "right":
                setPlayerRightX = (checkPlayerRightSide + entity.entitySpeed)/game.scaledTileSize;
                setPlayerBottomY = (checkPlayerBottomSide-entity.entitySpeed)/game.scaledTileSize;
                setPlayerTopY = (checkPlayerTopSide+entity.entitySpeed)/game.scaledTileSize;
                checkTile1 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerTopY];
                checkTile2 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerBottomY];
                if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                    entity.entityCollision = true;
                }
                break;
            case "up":
                setPlayerTopY = (checkPlayerTopSide - entity.entitySpeed)/game.scaledTileSize;
                setPlayerRightX = (checkPlayerRightSide - entity.entitySpeed)/game.scaledTileSize;
                setPlayerLeftX = (checkPlayerLeftSide + entity.entitySpeed)/game.scaledTileSize;
                checkTile1 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerTopY];
                checkTile2 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerTopY];
                if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                    entity.entityCollision = true;
                }
                break;
            case "down":
                setPlayerBottomY = (checkPlayerBottomSide + entity.entitySpeed)/game.scaledTileSize;
                setPlayerRightX = (checkPlayerRightSide - entity.entitySpeed)/game.scaledTileSize;
                setPlayerLeftX = (checkPlayerLeftSide + entity.entitySpeed)/game.scaledTileSize;
                checkTile1 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerBottomY];
                checkTile2 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerBottomY];
                if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                    entity.entityCollision = true;
                }
                break;
        }
    }

    public int checkEntityCollision(EnititesInfo entity, EnititesInfo[] targetEntity, final String direction){
        int index = 999;

        for (int es = 0; es < targetEntity.length; es++) {
            if (targetEntity[es] != null) {
                entity.hitbox.x = entity.entityWorldX + entity.hitbox.x;
                entity.hitbox.y = entity.entityWorldY + entity.hitbox.y;
                targetEntity[es].hitbox.x = targetEntity[es].entityWorldX + targetEntity[es].hitbox.x;
                targetEntity[es].hitbox.y = targetEntity[es].entityWorldY + targetEntity[es].hitbox.y;

                if (direction.equals("up")) {
                    entity.hitbox.y -= entity.entitySpeed;
                }
                if (direction.equals("down")) {
                    entity.hitbox.y += entity.entitySpeed;
                }
                if (direction.equals("left")) {
                    entity.hitbox.x -= entity.entitySpeed;
                }
                if (direction.equals("right")) {
                    entity.hitbox.x += entity.entitySpeed;
                }
                if (entity.hitbox.intersecting(targetEntity[es].hitbox)) {
                    entity.entityCollision = true;
                    index = es;
                }

                entity.hitbox.x = entity.entityDefaultHitboxLeft;
                entity.hitbox.y = entity.entityDefaultHitboxTop;
                targetEntity[es].hitbox.x = targetEntity[es].entityDefaultHitboxLeft;
                targetEntity[es].hitbox.y = targetEntity[es].entityDefaultHitboxTop;

            }
        }

        return index;
    }
}
