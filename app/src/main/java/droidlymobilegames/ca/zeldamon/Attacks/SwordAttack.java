package droidlymobilegames.ca.zeldamon.Attacks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import droidlymobilegames.ca.zeldamon.Entities.EnititesInfo;
import droidlymobilegames.ca.zeldamon.Game;

public class SwordAttack extends EnititesInfo {

    public Game game;
    public SwordAttack(Game game){
        this.game = game;
        setupSword();
    }

    public void setupSword(){
        hitboxrect.setColor(Color.BLUE);
        hitboxrect.setStyle(Paint.Style.FILL);
        hitbox.x = 0;
        hitbox.width = game.scaledTileSize;
        hitbox.y = 30;
        hitbox.height = game.scaledTileSize-30;
        entityCurrentDirection = "right";
        entityHealth = 1;
    }

    public void updateSword(){
        attackScreenX = entityWorldX - game.player.entityWorldX + game.player.attackScreenX;
        attackScreenY = entityWorldY - game.player.entityWorldY + game.player.attackScreenY;
        /*entityWorldX = game.player.entityWorldX + game.scaledTileSize;
        entityWorldY = game.player.entityWorldY + game.player.hitbox.y;*/
        //right attack
    }
    public void draw(Canvas canvas){
        int check = game.collisionHelper.checkEntityCollision(this,game.enemyLikeLikes,entityCurrentDirection);
        if (entityHealth>0) {//entityhealth can be set for sword attacks so that the sword doesn't keep attacking after it has 0 health
            checkAttack(check);
        }
        canvas.drawRect(attackScreenX + hitbox.x,
                attackScreenY + hitbox.y,
                attackScreenX + hitbox.x + hitbox.width,
                attackScreenY + hitbox.y + hitbox.height, hitboxrect);
    }

    public void checkAttack(final int i){
        entityHealth = 0;
        if (i!=999){
            //game.enemyLikeLikes[i] = null;
            //game.enemyLikeLikes[i].entityWorldX += 20;
            game.enemyLikeLikes[i].beingAttacked = true;
        }
    }
}
