package dar.jumper2;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Blood {

    private int x;
    private int y;
    private int height;
    private Animation animation = new Animation();

    Blood(Bitmap res, int x, int y, int w, int h, int numFrames)
    {
        this.x = x;
        this.y = y;
        this.height = h;

        Bitmap[] image = new Bitmap[numFrames];

        for(int i = 0; i<image.length; i++)
        {
            image[i] = Bitmap.createBitmap(res, i* w, 0, w, height);
        }
        animation.setFrames(image);
        animation.setDelay(10);
    }

    void draw(Canvas canvas)
    {
        if(!animation.playedOnce())
        {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }

    }
    void update()
    {
        if(!animation.playedOnce())
        {
            animation.update();
        }
    }
    public int getHeight(){return height;}
}

