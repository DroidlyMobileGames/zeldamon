package droidlymobilegames.ca.zeldamon;

import droidlymobilegames.ca.zeldamon.Entities.EnititesInfo;

public class CollisionHelper extends EnititesInfo {

    public CollisionHelper(Game game){
        this.game = game;

    }

    public void checkTileCollision(EnititesInfo entity,final String entityDirection){
        //Check Right & Left
        int checkPlayerLeftSide = entity.entityWorldX;
        int checkPlayerRightSide = entity.entityWorldX + game.scaledTileSize;
        int setPlayerLeftX = checkPlayerLeftSide/game.scaledTileSize;
        int setPlayerRightX = (checkPlayerRightSide/game.scaledTileSize)-1;
        //Check Up & Down
        int checkPlayerTopSide = entity.entityWorldY;
        int checkPlayerBottomSide = entity.entityWorldY + game.scaledTileSize;
        int setPlayerTopY = checkPlayerTopSide/game.scaledTileSize;
        int setPlayerBottomY = (checkPlayerBottomSide/game.scaledTileSize)-1;//To round the current Y value we can -1 so that when the player is walking left or right they don't collide
        //When we go left or right we want to check the top tile and bottom tile position so that the player cannot walk through the tile halfway
        if (entityDirection.equals("left")) {
            setPlayerLeftX = (checkPlayerLeftSide - entity.entitySpeed)/game.scaledTileSize;
            checkTile1 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerTopY];
            checkTile2 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerBottomY];
            if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                entity.entityCollision = true;
            }
        }
        if (entityDirection.equals("right")) {
            setPlayerRightX = checkPlayerRightSide/game.scaledTileSize;
            checkTile1 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerTopY];
            checkTile2 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerBottomY];
            if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                entity.entityCollision = true;
            }
        }

        if (entityDirection.equals("up")) {
            setPlayerTopY = (checkPlayerTopSide - entity.entitySpeed)/game.scaledTileSize;
            checkTile1 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerTopY];
            checkTile2 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerTopY];
            if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                entity.entityCollision = true;
            }
        }
        if (entityDirection.equals("down")) {
            setPlayerBottomY = checkPlayerBottomSide/game.scaledTileSize;
            checkTile1 = game.tileManagement.worldTileNum[setPlayerRightX][setPlayerBottomY];
            checkTile2 = game.tileManagement.worldTileNum[setPlayerLeftX][setPlayerBottomY];
            if (game.tileManagement.tileInfo[checkTile1].tileCollision || game.tileManagement.tileInfo[checkTile2].tileCollision){
                entity.entityCollision = true;
            }
        }
    }
}
