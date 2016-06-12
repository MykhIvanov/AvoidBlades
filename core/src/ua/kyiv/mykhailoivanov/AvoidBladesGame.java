package ua.kyiv.mykhailoivanov;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.kyiv.mykhailoivanov.screens.GameScreen;
import ua.kyiv.mykhailoivanov.screens.IntroScreen;
import ua.kyiv.mykhailoivanov.screens.MainMenuScreen;

public class AvoidBladesGame extends Game {

    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;
    private IntroScreen introScreen;
    public SpriteBatch batch;
    public Sound moveSound;
    public Sound buttonSound;
    public boolean canPlayMoveSound;

    public static float SCORE_TEXT_SCALING;

    private Preferences preferences;

    private int record;

    public boolean madenRecord;

    public int getRecord()
    {
        return record;
    }

    public void setScore(int score)
    {
        if (score > record) {
            record = score;
            madenRecord = true;
        }
    }



    public MainMenuScreen getMainMenuScreen()
    {
        return mainMenuScreen;
    }

    public GameScreen getGameScreen()
    {
        return gameScreen;
    }



	@Override
	public void create()
    {
        preferences = Gdx.app.getPreferences("AvoidBladesPreferences");

        loadRecord();

        SCORE_TEXT_SCALING = Gdx.graphics.getWidth()/ 180;

        batch = new SpriteBatch();
        gameScreen = new GameScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        introScreen = new IntroScreen(this);

        //
        madenRecord = false;
        //

        moveSound = Gdx.audio.newSound(Gdx.files.internal("sound/move.wav"));
        canPlayMoveSound = true;

        buttonSound = Gdx.audio.newSound(Gdx.files.internal("sound/button.wav"));


        setScreen(introScreen);

	}

    public void dispose(){
        super.dispose();
        saveRecord();
        moveSound.dispose();
        buttonSound.dispose();
    }

    public void render()
    {
        super.render();
    }



    private void loadRecord()
    {
        record = preferences.getInteger("RECORD", 0);
    }

    private void saveRecord()
    {
        preferences.putInteger("RECORD", record);
        preferences.flush();
    }


}
