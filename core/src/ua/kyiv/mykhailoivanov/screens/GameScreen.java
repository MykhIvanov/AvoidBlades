package ua.kyiv.mykhailoivanov.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;

import java.io.BufferedInputStream;
import java.io.InputStream;

import ua.kyiv.mykhailoivanov.AvoidBladesGame;
import ua.kyiv.mykhailoivanov.Button;
import ua.kyiv.mykhailoivanov.Fingure;
import ua.kyiv.mykhailoivanov.Score;
import ua.kyiv.mykhailoivanov.gesture_detectors.GameGestures;
import ua.kyiv.mykhailoivanov.obstacles.Blade;

/**
 * Created by MykhailoIvanov on 3/28/2016.
 */
public class GameScreen implements Screen {
    private AvoidBladesGame game;
    private Texture backgroundTexture;
    private Blade[] blade;
    private Fingure fingure;
    private Score score;
    private int bladesNumber;
    private GestureDetector gameGestureDetector;
    private GameGestures gameGestures;
    public boolean started;
    private Texture bloodTexture;
    private Sound cutSound;
    private boolean canPlaySound;
    private Texture tapToStartTexure;

    public static boolean stopTouched;


    public static boolean loose;

    public GameScreen(AvoidBladesGame gameInput) {
        game = gameInput;

        gameGestures = new GameGestures(game);
        gameGestureDetector = new GestureDetector(gameGestures);

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        bladesNumber = 3;
        blade = new Blade[bladesNumber];
        score = new Score(game);
        fingure = new Fingure(score);

        for (int i = 0; i < bladesNumber; i++)
            blade[i] = new Blade(i);


        tapToStartTexure = new Texture(Gdx.files.internal("taptostart.png"));

        bloodTexture = new Texture(Gdx.files.internal("blood3.png"));
        cutSound = Gdx.audio.newSound(Gdx.files.internal("sound/cut.wav"));

    }

    public void show() {
        game.madenRecord = false;
        secondCounter = 0;
        score.makeScoreZero();
        loose = false;
        stopTouched = false;
        game.canPlayMoveSound = true;
        game.madenRecord = false;
        started = false;
        fingure.setNoPos();
        Gdx.input.setInputProcessor(gameGestureDetector);

        for (int i = 0; i < bladesNumber; i++) {
            blade[i].refreshStartParams(i);
        }
        bloodSize = 0;
        infoText_posY = 0;
        canPlaySound = true;
        game.canPlayMoveSound = true;
    }

    private float secondCounter;

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();


        if (stopTouched)
        {
            drawAnimationOut();
        }
        else if (loose)
        {
            if (canPlaySound)
            {
                cutSound.play();
                canPlaySound = false;
            }
            game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            for (int i = 0; i < bladesNumber; i++)
                blade[i].draw(game.batch);
            drawBloodAnimation();
        }
        else {
            if (started) {
                secondCounter += delta;
                if (secondCounter >= 0.5) {
                    secondCounter -= 0.5;
                    score.increaseValue();
                }
                drawTheGameStarted();
            } else {
                drawTheGameWaiting();
            }
        }

        game.batch.end();
    }

    public static float bloodX;
    public static float bloodY;

    private static int bloodSize;

    private float bloodSizeChanging;

    private void drawBloodAnimation() {
        game.batch.draw(bloodTexture, bloodX, bloodY, bloodSize, bloodSize);
        bloodSizeChanging = (float) (Gdx.graphics.getWidth() / 67.5);
        bloodSize += bloodSizeChanging;
        bloodX -= bloodSizeChanging / 2;
        bloodY -= bloodSizeChanging / 2;

        for (int i = 0; i < bladesNumber; i++)
        {
            blade[i].goAwayFromScreen();
            blade[i].draw(game.batch);
        }


        if (bloodSize > Gdx.graphics.getWidth() / 2) // game finish
        {
            game.getMainMenuScreen().startAnimationFromGameMenu = true;
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private float infoText_posY;

    private void drawAnimationOut() {
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        score.drawOut(game.batch);

        for (int i = 0; i < bladesNumber; i++)
            blade[i].draw(game.batch);

        if (infoText_posY < Gdx.graphics.getWidth())
        {
            for (int i = 0; i < bladesNumber; i++)
                blade[i].goAwayFromScreen();
            infoText_posY += Button.ANIMATION_SPEED;
        }
        else
        {
            game.getMainMenuScreen().startAnimationFromGameMenu = true;
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private void drawTheGameWaiting()
    {
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(tapToStartTexure, Gdx.graphics.getWidth()/3, infoText_posY, Gdx.graphics.getWidth()/3,Gdx.graphics.getWidth()/3);
        score.draw(game.batch);

        if (infoText_posY <= Gdx.graphics.getWidth()/3)
            infoText_posY+= Button.ANIMATION_SPEED;
    }

    private void drawTheGameStarted() {
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (int i = 0; i < bladesNumber; i++) {
            blade[i].calculatePosition(blade[(i + 1) % 3].getPosX(), blade[(i + 2) % 3].getPosX());
            blade[i].draw(game.batch);
        }
        fingure.checkCollision(blade[0].getPosX(), blade[0].getY(), blade[1].getPosX(), blade[1].getY(), blade[2].getPosX(), blade[2].getY(), blade[0].getWidth(), blade[0].getHeight());

        fingure.draw(game.batch);

        score.draw(game.batch);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        game.setScore(score.getValue());
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        fingure.dispose();
        score.dispose();
        for (int i = 0; i < blade.length; i++)
            blade[i].dispose();

        bloodTexture.dispose();
        cutSound.dispose();
    }

    public void getReadyForAnimationSlide()
    {
        score.getReadyForAnimationSlide();
    }

}
