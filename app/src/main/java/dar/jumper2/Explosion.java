package dar.jumper2;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {
    private int x;
    private int y;
    private int height;
    private int row;
    private Animation animation = new Animation();

    Explosion(Bitmap res, int x, int y, int w, int h, int numFrames) {
        this.x = x;
        this.y = y;
        this.height = h;

        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {
            if (i % 5 == 0 && i > 0) row++;
            image[i] = Bitmap.createBitmap(res, (i - (5 * row)) * w, row * height, w, height);
        }
        animation.setFrames(image);
        animation.setDelay(50);


    }

    void draw(Canvas canvas) {
        if (!animation.playedOnce()) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }

    }

    void update() {
        if (!animation.playedOnce()) {
            animation.update();
        }
    }

    public int getHeight() {
        return height;
    }
}


