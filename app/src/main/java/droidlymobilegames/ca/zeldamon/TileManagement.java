package droidlymobilegames.ca.zeldamon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TileManagement extends TileInfo{

    public TileInfo[] tileInfo = new TileInfo[35];

    public TileManagement (Game game){
        this.game = game;
        loadTilesheet();
        worldTileNum = new int[game.maxColumns][game.maxRows];
        loadMap("worlddummy");
    }

    public void drawTiles(Canvas canvas){
        int tileCol = 0;
        int tileRow = 0;
        while (tileCol < game.maxColumns && tileRow < game.maxRows){
            tileNum = worldTileNum[tileCol][tileRow];//Gets the tileNum at the XY position from the txt data
            int tileWorldX = tileCol * game.scaledTileSize;//Sets the tile at the position X in the world times the scaled tilesize 160 in example
            int tileWorldY = tileRow * game.scaledTileSize;//Sets position Y times scaled tilesize
            int tileScreenX = tileWorldX - game.player.entityWorldX + game.player.entityScreenX;
            int tileScreenY = tileWorldY - game.player.entityWorldY + game.player.entityScreenY;

            if (tileWorldX + game.gameScreenWidth > game.player.entityWorldX - game.player.entityScreenX &&
                    tileWorldX - game.gameScreenWidth < game.player.entityWorldX + game.player.entityScreenX &&
                    tileWorldY + game.gameScreenHeight > game.player.entityWorldY - game.player.entityScreenY &&
                    tileWorldY - game.gameScreenHeight < game.player.entityWorldY + game.player.entityScreenY){
                if (tileInfo[tileNum].defaultTileimg != null) {
                    canvas.drawBitmap(tileInfo[tileNum].defaultTileimg, tileScreenX, tileScreenY, null);
                }
            }
            tileCol ++;

            if (tileCol == game.maxColumns){//Check if tileCol reaches the end in this case 100 tiles then resets back to 0 then increases rows
                tileCol = 0;
                tileRow++;
            }
        }

    }

    public void loadMap(final String _mapname){//Used to load map from the raw folder in res
        try {
            inputStream = game.getContext().getResources().openRawResource(
                    game.getContext().getResources().getIdentifier(
                            _mapname,"raw", game.getContext().getPackageName()));
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column< game.maxColumns && row < game.maxRows){
                String line = bufferedReader.readLine();
                while (column < game.maxColumns){
                    //Splits the line to read the data from the text
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    worldTileNum[column][row]= num;
                    column ++;
                }
                if (column == game.maxColumns){
                    column = 0;
                    row ++;
                }
            }
            bufferedReader.close();
        }catch (Exception e){
        }
    }

    public void loadTilesheet(){
        Bitmap tilesheet;
        int col1 = 0;
        int row1 = 0;
        int numoftiles = 0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        tilesheet = BitmapFactory.decodeResource(game.getResources(),R.drawable.bottom_layer1_tilesheet,bitmapOptions);
        int maxcol1 = tilesheet.getWidth()/16;
        int maxrow1 = tilesheet.getHeight()/16;
        while (row1 < maxrow1){
            tileImgs[numoftiles] = Bitmap.createScaledBitmap(Bitmap.createBitmap
                    (tilesheet,col1 * 16,row1 * 16,16,16),game.scaledTileSize,game.scaledTileSize,false);
            col1 ++;
            if (col1 == maxcol1){
                col1 = 0;
                row1 ++;
            }
            numoftiles ++;
            tilesList.add(numoftiles);
        }
        setUpTileInfo();//After tiles are loaded from tilesheet all tile details are setup
    }

    public void setUpTileInfo(){//This is used to check tiles that are collidable
        collisionTiles.add(String.valueOf(1));
        for (int tileID = 0; tileID<tilesList.size(); tileID++){
            tileInfo[tileID] = new TileInfo();
            tileInfo[tileID].defaultTileimg = tileImgs[tileID];
            if (collisionTiles.contains(String.valueOf((int)tileID))){
                tileInfo[tileID].tileCollision = true;
            }
        }
    }
}
