package dar.jumper2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;
import static android.content.Context.MODE_PRIVATE;
import static android.view.MotionEvent.*;
import static dar.jumper2.Game.highscore;
import static dar.jumper2.Game.highscorePref;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 5116;
    public static final int HEIGHT = 1440;
    public static final int MOVESPEED = -5;


    private long meteorStartTime;
    private MainThread thread;
    private Background bg;
    private Bottom bottom;
    private Player player;
    private Blood blood;
    private boolean dead=false;
    private ArrayList<Meteor> meteors;
    private Random rand = new Random();
    private boolean newGameCreated;
    private boolean collide=false;
    private Explosion explosion;


    public GamePanel(Context context) {
        super(context);
        //add the callback to the surface holder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int heigth) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.stars_background));
        bottom = new Bottom(BitmapFactory.decodeResource(getResources(), R.drawable.mondoberflache));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.astronaut_sprites), 120, 280, 8);


        //int bitMap1= R.drawable.figur_sprites;

        meteors = new ArrayList<>();


        meteorStartTime = System.nanoTime();
        //safely start the game
        thread.setRunning(true);
        thread.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == ACTION_DOWN) {
            player.setOnGround(false);
            if (!player.getPlaying()) {
                player.setPlaying(true);
                newGame();

            } else {
                player.setUp(true);
                return true;
            }
        }

        if (event.getAction() == ACTION_UP) {

            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = (float) getWidth() / WIDTH * 2.3f;
        final float scaleFactorY = (float) getHeight() / HEIGHT;
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            //super.draw(null);
            bg.draw(canvas);
            bottom.draw(canvas);
            player.draw(canvas);

            if(dead) {
                blood.draw(canvas);
            }

            if(collide)
            {
                explosion.draw(canvas);
                collide=false;
            }

            for (Meteor m : meteors) {
                m.draw(canvas);
            }


            drawText(canvas);


            //Verhindern damit das Bild nicht immer weiter gescaled wird
            canvas.restoreToCount(savedState);
        }

    }

    public void update() {
        if (player.getPlaying()) {
            bg.update();
            bottom.update();
            player.update();



            //spawnt Meteore nach einer gewissen Zeit
            long meteorTimeElapsed = (System.nanoTime() - meteorStartTime) / 1000000;
            if (meteorTimeElapsed > (1100 - player.getScore() / 4)) {

                //erster Meteor IMMMER in der Mitte
                if (meteors.size() == 0) {
                    meteors.add(new Meteor(BitmapFactory.decodeResource(getResources(), R.drawable.meteors), WIDTH + 10, 10, 140, 173, player.getScore(), 9));
                } else {
                    meteors.add(new Meteor(BitmapFactory.decodeResource(getResources(), R.drawable.meteors), (int) (rand.nextDouble() * WIDTH + 1500 - WIDTH / 2), 10, 140, 173, player.getScore(), 9));
                }
                //reset timer
                meteorStartTime = System.nanoTime();
            }


            for (int i = 0; i < meteors.size(); i++) {
                meteors.get(i).update();


                if (meteorCollision(meteors.get(i), player)) {

                    meteors.remove(i);
                    player.setPlaying(false);
                    break;
                }


                //meteor wird entfernt, nachdem er den Bildschirm verlässt
                if (meteors.get(i).getX() < -100) {
                    meteors.remove(i);
                    break;
                }

                if(meteors.get(i).getY() >= 1210)
                {
                    collide = true;
                    explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion_sprites), meteors.get(i).getY(), 1210, 105, 140, 25);
                    explosion.update();
                    meteors.remove(i);
                    break;
                }
            }
        } else {
            blood = new Blood(BitmapFactory.decodeResource(getResources(),R.drawable.blood_sprites), player.getX()-80, player.getY()-50,272,280,6);
            blood.update();

            newGameCreated = false;
        }
    }

    public void newGame() {
        dead=false;
        if (player.getScore() > highscore) {
            System.out.println("HIGHSCORE ÜBERNOMMEN");
            highscorePref.edit().putInt("highscore",player.getScore()).apply();
            highscore = highscorePref.getInt("highscore", MODE_PRIVATE);
        }


        meteors.clear();
        player.resetScore();
        player.resetDY();
        player.setY(HEIGHT - 280);

        newGameCreated = true;


    }


    public boolean meteorCollision(GameObject a, GameObject b) {

        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            System.out.println("METEOR COLLISION");
            System.out.print("GAME OVER!");
            dead=true;

            return true;
        }
        return false;
    }

    public void drawText(Canvas canvas) {
        //Typeface tf =Typeface.createFromAsset(getContext().getAssets(), "fonts/DINTrek.ttf");
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(60);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE:" + player.getScore(), 10, HEIGHT - 20, paint);
        canvas.drawText("SESSION HIGHSCORE:" + highscore, 1300, HEIGHT - 20, paint);

        if (!player.getPlaying() && newGameCreated) {
            Paint paint1 = new Paint();
            paint1.setTextSize(20);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Press TO START", WIDTH / 2 - 50, HEIGHT / 2 + 40, paint1);


            paint1.setTextSize(20);
            /*canvas.drawText(("Press TO START", WIDTH / 2 - 50, HEIGHT / 2 + 40, paint1);
            canvas.drawText(("Press TO START", WIDTH / 2 - 50, HEIGHT / 2 + 40, paint1);*/

        }


    }
}