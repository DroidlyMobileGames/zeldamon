package droidlymobilegames.ca.zeldamon.Entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class EnititesInfo {

    public int entityPosX, entityPosY = 0;
    public int entityWorldX,entityWorldY = 0;
    public int entityScreenX, entityScreenY = 0;
    public int entityHealth = 0;
    public int entitySpeed = 0;
    public int entityAnimNum = 1;
    public int entityAnimCounter = 0;
    public int entityMaxAnimCount = 0;

    public Bitmap[] entitySprites = new Bitmap[500];
    public Bitmap defaultEntitySprite = null;
    public ArrayList<Integer> numoftiles = new ArrayList<>();
}
