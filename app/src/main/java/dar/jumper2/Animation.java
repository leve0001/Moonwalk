package dar.jumper2;

import android.graphics.Bitmap;

class Animation {

    private Bitmap[]frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    void setDelay(long d)
    {
        delay = d;
    }

    void update()
    {
        long elapsed = (System.nanoTime() - startTime)/1000000;

        if (elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }

    Bitmap getImage(){
        return frames[currentFrame];
    }

    boolean playedOnce(){
        return playedOnce;
    }

}
