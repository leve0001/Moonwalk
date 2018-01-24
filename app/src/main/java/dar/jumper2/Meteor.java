package dar.jumper2;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

class Meteor extends GameObject{


    private int speed;
    private Animation animation = new Animation();

    Meteor(Bitmap res, int x, int y, int w, int h, int s, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        //Basis Geschwindigkeit + prozentuales Erhöhen
        Random rand = new Random();
        speed = 7 + (int) (rand.nextDouble() * s / 30);

        //Geschwindigkeitsbegrenzung
        if (speed > 40) speed = 40;


        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(res, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(60);
    }

        void update()
    {
        x-=speed;
        y+=speed/2;
        animation.update();
    }

    void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception ignored)
        {

        }
    }
}
