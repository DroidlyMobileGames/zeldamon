package droidlymobilegames.ca.zeldamon.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import droidlymobilegames.ca.zeldamon.Game;
import droidlymobilegames.ca.zeldamon.Hitbox;

public class EnititesInfo {

    public Game game;
    public Hitbox hitbox = new Hitbox();
    public Paint hitboxrect = new Paint();
    public Paint attackPaint = new Paint();

    public int entityPosX, entityPosY = 0;
    public int entityWorldX,entityWorldY = 0;
    public int entityScreenX, entityScreenY = 0;
    public int entityHealth = 0;
    public int entitySpeed = 0;
    public int entityAnimNum = 1;
    public int entityAnimCounter = 0;
    public int entityMaxAnimCount = 0;
    public int setNextDirection = 0;
    public int nextDirectionSpeed = 0;
    public int random = 0;
    public int entityAttackAnimNum = 1;
    public int entityAttackAnimCounter = 0;
    public int entityAttackMaxAnim = 0;
    public int attackScreenX = 0;
    public int attackScreenY = 0;
    public int entityDefaultHitboxLeft,entityDefaultHitboxTop = 0;
    public int entityDefaultHitboxRight,entityDefaultHitboxBottom = 0;
    //Collision checking
    public int checkTile1,checkTile2 = 0;
    public int resetAttackTimer = 0;
    public int checkBeingAttacked = 0;
    public int beingAttackedNum = 0;

    public String entityCurrentDirection = "";
    public String entityDefaultDirection = "";

    public boolean entityRight,entityLeft,entityUp,entityDown = false;
    public boolean entityCollision = false;
    public boolean entityIdle = false;
    public boolean showHitbox = false;
    public boolean entityAttacking = false;
    public boolean beingAttacked = false;

    public Bitmap[] entitySprites = new Bitmap[500];
    public Bitmap[] entityAttackSprites = new Bitmap[500];
    public Bitmap defaultEntitySprite = null;
    public ArrayList<Integer> numoftiles = new ArrayList<>();

    public void draw(Canvas canvas){
        if (defaultEntitySprite!=null){
            canvas.drawBitmap(defaultEntitySprite,attackScreenX,
                    attackScreenY,null);
        }
    }
}
