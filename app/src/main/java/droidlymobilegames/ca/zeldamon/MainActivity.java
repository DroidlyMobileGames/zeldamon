package droidlymobilegames.ca.zeldamon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    public Game game;
    public ImageView upbutton,downbutton,rightbutton,leftbutton;
    public LinearLayout dpad_main_layout;
    public int buttonwidthheight = 0;
    public RelativeLayout gameviewholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_layout);
        game = new Game(this);
        gameviewholder = findViewById(R.id.gameviewholder);
        gameviewholder.addView(game);

        dpad_main_layout = findViewById(R.id.dpad_main_layout);
        upbutton = findViewById(R.id.upbutton);
        downbutton = findViewById(R.id.downbutton);
        rightbutton = findViewById(R.id.rightbutton);
        leftbutton = findViewById(R.id.leftbutton);
        setupUILayouts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.gameLoop.stopLoop();
    }

    public void setupUILayouts(){
        buttonwidthheight = game.getDisplayWidth(this)/6;

        upbutton.getLayoutParams().width = buttonwidthheight;
        upbutton.getLayoutParams().height = buttonwidthheight;
        downbutton.getLayoutParams().width = buttonwidthheight;
        downbutton.getLayoutParams().height = buttonwidthheight;
        rightbutton.getLayoutParams().width = buttonwidthheight;
        rightbutton.getLayoutParams().height = buttonwidthheight;
        leftbutton.getLayoutParams().width = buttonwidthheight;
        leftbutton.getLayoutParams().height = buttonwidthheight;

        dpad_main_layout.getLayoutParams().width = buttonwidthheight*3;
        dpad_main_layout.getLayoutParams().height = buttonwidthheight*3;
    }
}