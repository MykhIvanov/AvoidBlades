package ua.kyiv.mykhailoivanov.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;

import ua.kyiv.mykhailoivanov.AvoidBladesGame;
import ua.kyiv.mykhailoivanov.Button;
import ua.kyiv.mykhailoivanov.Score;
import ua.kyiv.mykhailoivanov.gesture_detectors.MainMenuGestures;

/**
 * Created by MykhailoIvanov on 5/9/2016.
 */
public class MainMenuScreen implements Screen {
    private AvoidBladesGame game;
    private Texture backgroundTexture;

    private Button playButton;
    private Button exitButton;
    private GestureDetector gestureDetectorForMainMenu;
    private MainMenuGestures mainMenuGestures;

    public boolean startAnimationToGameMenu;
    public boolean startAnimationFromGameMenu;

    private Score recordObject;

    // newrecord texture
    private Texture newRecordTexture;
    private float newRecordTexture_posX;
    private float newRecordTexture_posY;
    private float newRecordTexture_width;
    private float newRecordTexture_height;
    //

    public MainMenuScreen(AvoidBladesGame gameIn)
    {

        startAnimationToGameMenu = false;
        startAnimationFromGameMenu = false;

        game = gameIn;

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        newRecordTexture = new Texture(Gdx.files.internal("newrecord.png"));

        newRecordTexture_width = Gdx.graphics.getWidth() / 2;
        newRecordTexture_height = newRecordTexture_width * newRecordTexture.getHeight() / newRecordTexture.getWidth();
        newRecordTexture_posX = Score.MAIN_POS_X;
        newRecordTexture_posY = Score.MAIN_POS_Y;


        recordObject = new Score(game);

        playButton = new Button(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth() / 4 / 2, 0, Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4, "playBTN.png", "playBTN_pressed.png");
        exitButton = new Button(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth() / 4 / 2, 0 - Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4, "exitBTN.png", "exitBTN_pressed.png");

        mainMenuGestures = new MainMenuGestures();
        mainMenuGestures.setGameScreen(game);
        mainMenuGestures.setButtons(playButton, exitButton);
        gestureDetectorForMainMenu = new GestureDetector(mainMenuGestures);
    }

    public void dispose()
    {
        playButton.dispose();
        exitButton.dispose();
        backgroundTexture.dispose();
        newRecordTexture.dispose();
    }

    public void hide()
    {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gestureDetectorForMainMenu);
        startAnimationFromGameMenu = true;
    }


    public void render(float delta)
    {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if (startAnimationToGameMenu)
            animationOut();
        else if (startAnimationFromGameMenu)
            animationIn();
        else
        {
            game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            playButton.draw(game.batch);
            exitButton.draw(game.batch);
            if (game.madenRecord)
                game.batch.draw(newRecordTexture, newRecordTexture_posX, newRecordTexture_posY, newRecordTexture_width, newRecordTexture_height);
            recordObject.drawRecord_mainMenu(game.batch);
        }

        game.batch.end();
    }

    private void animationOut()
    {
        startAnimationFromGameMenu = false; // to safe from crash in some cases

        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playButton.draw_animationOut(game.batch);
        exitButton.draw_animationOut(game.batch);

        if (playButton.getPosY() + playButton.getWidth() < 0)
        {
            this.startAnimationToGameMenu = false;
            game.setScreen(game.getGameScreen());
        }
        recordObject.drawRecord_mainMenu_animation(game.batch);
        game.getGameScreen().getReadyForAnimationSlide(); // @TEST
    }

    private void animationIn()
    {
        startAnimationToGameMenu = false; // to safe from crash in some cases

        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playButton.draw_animationIn(game.batch);
        exitButton.draw_animationIn(game.batch);

        if (playButton.getPosY() >= Gdx.graphics.getHeight()/2)
        {
            this.startAnimationFromGameMenu = false;
        }
        recordObject.drawRecord_mainMenu_animation(game.batch);
    }


    public void resize(int width, int height) {

    }

    public void pause()
    {

    }


    public void resume() {

    }



}
