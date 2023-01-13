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
        checkPlayerLeftSide = entity.entityWorldX + entity.hitbox.left;
        checkPlayerRightSide = entity.entityWorldX + entity.hitbox.left + entity.hitbox.right;
        setPlayerLeftX = checkPlayerLeftSide/game.scaledTileSize;
        setPlayerRightX = checkPlayerRightSide/game.scaledTileSize;
        //Check Up & Down
        checkPlayerTopSide = entity.entityWorldY + entity.hitbox.top;
        checkPlayerBottomSide = entity.entityWorldY + entity.hitbox.top + entity.hitbox.bottom;
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
}
