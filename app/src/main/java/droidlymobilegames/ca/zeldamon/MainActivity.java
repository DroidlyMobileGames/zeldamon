package droidlymobilegames.ca.zeldamon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Game game;
    public ImageView upbutton,downbutton,rightbutton,leftbutton,attackbutton;
    public LinearLayout dpad_main_layout;
    public int buttonwidthheight = 0;
    public RelativeLayout gameviewholder;
    public TextView testtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this);
        setContentView(R.layout.top_layout);

        gameviewholder = findViewById(R.id.gameviewholder);
        gameviewholder.addView(game);

        dpad_main_layout = findViewById(R.id.dpad_main_layout);
        upbutton = findViewById(R.id.upbutton);
        downbutton = findViewById(R.id.downbutton);
        rightbutton = findViewById(R.id.rightbutton);
        leftbutton = findViewById(R.id.leftbutton);
        attackbutton = findViewById(R.id.attackbutton);
        testtext = findViewById(R.id.testtext);
        setupUILayouts();
        setTouchEvents();
        getGameControllerIds();

    }

    @Override
    protected void onPause() {
        super.onPause();
        game.gameLoop.stopLoop();
    }

    public void setupUILayouts(){
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            buttonwidthheight = game.getDisplayWidth(this)/6;
        } else {
            buttonwidthheight = game.getDisplayHeight(this)/6;
        }

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

        attackbutton.getLayoutParams().width = buttonwidthheight;
        attackbutton
                .getLayoutParams().height = buttonwidthheight;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setTouchEvents(){
        rightbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!game.player.entityAttacking){
                            game.checkbuttonpressed = true;
                            game.player.entityRight = true;
                            game.checkbutton = "right";
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = false;
                            game.player.entityRight = false;
                        }
                        game.checkbutton = "none";
                        break;
                }
                return true;
            }
        });
        leftbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!game.player.entityAttacking){
                            game.checkbuttonpressed = true;
                            game.player.entityLeft = true;
                            game.checkbutton = "left";
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = false;
                            game.player.entityLeft = false;
                        }
                        game.checkbutton = "none";
                        break;
                }
                return true;
            }
        });

        downbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!game.player.entityAttacking){
                            game.checkbuttonpressed = true;
                            game.player.entityDown = true;
                            game.checkbutton = "down";
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = false;
                            game.player.entityDown = false;
                        }
                        game.checkbutton = "none";
                        break;
                }
                return true;
            }
        });
        upbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!game.player.entityAttacking){
                            game.checkbuttonpressed = true;
                            game.player.entityUp = true;
                            game.checkbutton = "up";
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = false;
                            game.player.entityUp = false;
                        }
                        game.checkbutton = "none";
                        break;
                }
                return true;
            }
        });

        attackbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = true;
                            game.player.entityAttacking = true;
                            game.player.entityDown = false;
                            game.player.entityUp = false;
                            game.player.entityRight = false;
                            game.player.entityLeft = false;
                            game.player.entityAttackAnimCounter = 0;
                            game.player.entityAttackAnimNum = 1;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //game.checkbuttonpressed = false;
                        //game.player.entityAttacking = false;

                        break;
                }
                return true;
            }
        });

    }
    public ArrayList<Integer> getGameControllerIds() {
        ArrayList<Integer> gameControllerDeviceIds = new ArrayList<Integer>();
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();

            // Verify that the device has gamepad buttons, control sticks, or both.
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                dpad_main_layout.setVisibility(View.GONE);
                attackbutton.setVisibility(View.GONE);
                // This device is a game controller. Store its device ID.
                if (!gameControllerDeviceIds.contains(deviceId)) {
                    gameControllerDeviceIds.add(deviceId);
                }
            }
        }
        return gameControllerDeviceIds;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case 23:

                break;
            case 19:
                if (!game.player.entityAttacking) {
                    game.checkbuttonpressed = false;
                    game.player.entityUp = false;
                }
                game.checkbutton = "none";
                break;
            case 20:
                if (!game.player.entityAttacking) {
                    game.checkbuttonpressed = false;
                    game.player.entityDown = false;
                }
                game.checkbutton = "none";
                break;
            case 21:
                if (!game.player.entityAttacking) {
                    game.checkbuttonpressed = false;
                    game.player.entityLeft = false;
                }
                game.checkbutton = "none";
                break;
            case 22:
                if (!game.player.entityAttacking) {
                    game.checkbuttonpressed = false;
                    game.player.entityRight = false;
                }
                game.checkbutton = "none";
                break;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if (keyEvent.getRepeatCount() == 0){
            switch (keyCode){
                /*case 23://A*/
                case KeyEvent.KEYCODE_A://Key number is 29
                    if (!game.player.entityAttacking) {
                        game.checkbuttonpressed = true;
                        game.player.entityAttacking = true;
                        game.player.entityDown = false;
                        game.player.entityUp = false;
                        game.player.entityRight = false;
                        game.player.entityLeft = false;
                        game.player.entityAttackAnimCounter = 0;
                        game.player.entityAttackAnimNum = 1;
                    }
                    break;
                case 4://B
                    break;
                case 62://Y
                    break;
                case 67://X
                    break;
                case 19://UP
                    if (game.checkbutton.equals("none")) {
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = true;
                            game.player.entityUp = true;
                            game.checkbutton = "up";
                        }
                    }
                    break;
                case 20://DOWN
                    if (game.checkbutton.equals("none")) {
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = true;
                            game.player.entityDown = true;
                            game.checkbutton = "down";
                        }
                    }
                    break;
                case 21://LEFT
                    if (game.checkbutton.equals("none")) {
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = true;
                            game.player.entityLeft = true;
                            game.checkbutton = "left";
                        }
                    }
                    break;
                case 22://RIGHT
                    if (game.checkbutton.equals("none")) {
                        if (!game.player.entityAttacking) {
                            game.checkbuttonpressed = true;
                            game.player.entityRight = true;
                            game.checkbutton = "right";
                        }
                    }
                    break;
            }

        }
        testtext.setText(String.valueOf(keyEvent.getKeyCode()));
        return super.onKeyDown(keyCode,keyEvent);
    }
}