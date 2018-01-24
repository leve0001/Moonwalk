package dar.jumper2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Background {

    private Bitmap image;
    private int x;
    private int dx;

    Background(Bitmap res)
    {
        image = res;
        dx = GamePanel.MOVESPEED;
    }

    void update()
    {
        x += dx*0.3;
        if (x <- GamePanel.WIDTH){
            x = 0;
        }
    }

    void draw(Canvas canvas)
    {
        int y = 0;
        canvas.drawBitmap(image, x, y, null);
        if (x < 0){
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }


}
