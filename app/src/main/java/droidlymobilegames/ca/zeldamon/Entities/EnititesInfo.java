package droidlymobilegames.ca.zeldamon.Entities;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import droidlymobilegames.ca.zeldamon.Game;

public class EnititesInfo {

    public Game game;
    public Rect hitbox = new Rect();
    public Paint hitboxrect = new Paint();

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

    //Collision checking
    public int checkTile1,checkTile2 = 0;

    public String entityCurrentDirection = "";
    public String entityDefaultDirection = "";

    public boolean entityRight,entityLeft,entityUp,entityDown = false;
    public boolean entityCollision = false;
    public boolean entityIdle = false;
    public boolean showHitbox = false;

    public Bitmap[] entitySprites = new Bitmap[500];
    public Bitmap defaultEntitySprite = null;
    public ArrayList<Integer> numoftiles = new ArrayList<>();
}
