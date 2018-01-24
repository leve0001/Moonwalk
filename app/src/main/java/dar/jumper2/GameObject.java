package dar.jumper2;


import android.graphics.Rect;

public abstract class GameObject {
    protected int x;
    int y;
    int dy;
    int width;
    protected int height;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    Rect getRectangle()
    {
        return new Rect(x,y, x+width, y+height);
    }
}
