package droidlymobilegames.ca.zeldamon;

import android.view.SurfaceHolder;
import android.graphics.*;

public class GameLoop extends Thread{
    public static final double MAX_UPS = 60.00;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    public Game game;
    public final SurfaceHolder surfaceHolder;

    private boolean isRunning = false;
    public double averageFPS;
    public long startTime;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }
    public double getAverageFPS() {
        return averageFPS;
    }

    @Override
    public void run() {
        super.run();
        // Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;
        long elapsedTime;
        long sleepTime;
        // Game loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning) {
            // Try to update and render game
            try {
                //canvas = surfaceHolder.lockCanvas();//Used for devices that cannot render on the GPU
                canvas = surfaceHolder.lockHardwareCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;
                    game.draw(canvas);

                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    frameCount++;
                }
            }
            // Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Skip frames to keep up with target UPS
            while(sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }
            // Calculates and shows the FPS (slows down gameplay when active)
            if (game.showFPS == true) {
                elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= 1000) {

                    averageFPS = frameCount / (1E-3 * elapsedTime);
                    updateCount = 0;
                    frameCount = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        }
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    public void stopLoop() {
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}