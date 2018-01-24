package dar.jumper2;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.SyncStateContract;
import android.util.DisplayMetrics;
import android.view.Display;

public class Player extends GameObject{

    private int score;

    private boolean up;
    private boolean onGround;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    Player(Bitmap res, int w, int h, int numFrames)
    {
        x = 400;
        y = GamePanel.HEIGHT-280-262/2;
        dy = 0;
        score =0;
        height = h;
        width = w;


        Bitmap[] image= new Bitmap[numFrames];

        for(int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(res, i*width, 0, width, height);
        }


        animation.setFrames(image);
        animation.setDelay(150);
        startTime = System.nanoTime();
    }

    public void setUp(boolean b) {
        up=b;
    }

    void setOnGround(boolean b) {
        onGround=b;
    }

    void update()
    {
        long elapsed =(System.nanoTime()-startTime)/1000000;
        if(elapsed>100) {
            score++;
            startTime=System.nanoTime();
        }
        animation.update();

        if(onGround)
        {
            dy +=0;
            //res = R.drawable.figur_sprites;
        }
        else {

            if (up) {
                dy -= 10;
            } else {
                dy += 3;
            }

            if (dy > 3) {
                dy = 3;
            }

            if (dy < -10) {
                dy = -10;
            }

            y += dy * 2;
            //dy = 0;

            //1400 ist Wert des Backgrounds
            if (y >= GamePanel.HEIGHT-280-262/2) {
                y = GamePanel.HEIGHT-280-262/2;
            }

            if (y <= (25 / 2)) {
                y = (25 / 2);
            }
        }
    }



    void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    int getScore() {
        return score;
    }

    boolean getPlaying() {
        return playing;
    }

    void setPlaying(boolean b) {
        playing=b;
    }

    void resetDY() {
        dy=0;
    }

    void resetScore() {
        score=0;
    }

}
