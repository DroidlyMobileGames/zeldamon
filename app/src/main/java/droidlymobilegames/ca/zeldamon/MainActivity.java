package droidlymobilegames.ca.zeldamon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.gameLoop.stopLoop();
    }
}