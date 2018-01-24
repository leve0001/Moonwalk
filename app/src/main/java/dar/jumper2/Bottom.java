package dar.jumper2;


import android.graphics.Bitmap;
import android.graphics.Canvas;

class Bottom {
    private Bitmap image;
    private int x, dx;

    Bottom(Bitmap res) {
        image = res;
        dx = GamePanel.MOVESPEED;
    }

    void update() {
        x += dx;
        if (x < -GamePanel.WIDTH) {
            x = 0;
        }
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, GamePanel.HEIGHT-263, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, GamePanel.HEIGHT-263, null);
        }
    }
}