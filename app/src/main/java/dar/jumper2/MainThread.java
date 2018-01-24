package dar.jumper2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    void setRunning(boolean b){
        running = b;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        int FPS = 60;
        long targetTime = 100/ FPS;

        while(running){
            startTime = System.nanoTime();
            Canvas canvas = null;

            //Try to lock canvas for pixel editing
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception ignored){}
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                sleep(waitTime);
            } catch (Exception ignored){}
            finally {
                if (canvas != null){
                    try{surfaceHolder.unlockCanvasAndPost(canvas);
                }catch (Exception e){e.printStackTrace();}
                }
            }


            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS){
                double averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
