package ua.kyiv.mykhailoivanov.gesture_detectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import ua.kyiv.mykhailoivanov.AvoidBladesGame;
import ua.kyiv.mykhailoivanov.Button;


/**
 * Created by MykhailoIvanov on 5/10/2016.
 */
public class MainMenuGestures implements GestureDetector.GestureListener {

    private Button exitButton;
    private Button playButton;
    private AvoidBladesGame game;


    public void setButtons(Button play, Button exit)
    {
        playButton = play;
        exitButton = exit;

    }

    public void setGameScreen(AvoidBladesGame gameInput)
    {
        game = gameInput;
    }


    public boolean touchDown(float x, float y, int pointer, int button)
    {
        // COORDINATES INVERSION
        y = Gdx.graphics.getHeight() - y;
        //
        if (playButton.wasClicked(x, y))
            playButton.touched = true;
        else if (exitButton.wasClicked(x, y))
            exitButton.touched = true;
        return false;
    }


    public boolean tap(float x, float y, int count, int button)
    {

        // COORDINATES INVERSION
        y = Gdx.graphics.getHeight() - y;
        //

        if (playButton.wasClicked(x, y))
        {
            game.buttonSound.play();
            game.getMainMenuScreen().startAnimationToGameMenu = true;
        }

        else if (exitButton.wasClicked(x, y))
        {
            game.buttonSound.play();
            Gdx.app.exit();
        }
        else
        {
            makeButtonsTextureUntouched();
        }


        return false;
    }


    private void makeButtonsTextureUntouched()
    {
        playButton.touched = false;
        exitButton.touched = false;
    }
    public boolean longPress(float x, float y)
    {
        return false;
    }


    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }


    public boolean pan(float x, float y, float deltaX, float deltaY) {
        makeButtonsTextureUntouched();
        return false;
    }


    public boolean panStop(float x, float y, int pointer, int button) {
        makeButtonsTextureUntouched();
        return false;
    }


    public boolean zoom(float initialDistance, float distance) {
        makeButtonsTextureUntouched();
        return false;
    }


    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
