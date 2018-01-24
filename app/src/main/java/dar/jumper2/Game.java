package dar.jumper2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class Game extends Activity {

    public static SharedPreferences highscorePref = null;
    public static int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set to Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        highscorePref = getSharedPreferences("highscore", MODE_PRIVATE);
        highscore = highscorePref.getInt("highscore", 0);

        setContentView(new GamePanel(this));
    }


}
