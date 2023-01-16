package droidlymobilegames.ca.zeldamon;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;

public class TileInfo {

    public InputStream inputStream;
    public BufferedReader bufferedReader;
    public Game game;
    public int worldTileNum[][];//Receives the tile at the XY positions in the world
    public int tileNum = 0;//This is to check the tile the player is interacting with
    public boolean tileCollision = false;//Collision of tile is always set to false unless set true
    public Bitmap defaultTileimg = null;//Default tile image drawn when cycling through world data
    public Bitmap[] tileImgs = new Bitmap[1000];//Add up to 1000 tile images
    public ArrayList<Integer> tilesList = new ArrayList<>();
    public ArrayList<String> collisionTiles = new ArrayList<>();

}
